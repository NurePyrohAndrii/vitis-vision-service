package com.vitisvision.vitisvisionservice.domain.block.service;

import com.vitisvision.vitisvisionservice.domain.block.dto.BlockReportRequest;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockRequest;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockResponse;
import com.vitisvision.vitisvisionservice.domain.block.entity.Block;
import com.vitisvision.vitisvisionservice.domain.block.exception.BlockDuplicationException;
import com.vitisvision.vitisvisionservice.domain.block.exception.BlockNotFoundException;
import com.vitisvision.vitisvisionservice.domain.block.mapper.BlockRequestMapper;
import com.vitisvision.vitisvisionservice.domain.block.mapper.BlockResponseMapper;
import com.vitisvision.vitisvisionservice.domain.block.repository.BlockRepository;
import com.vitisvision.vitisvisionservice.domain.device.entity.DeviceData;
import com.vitisvision.vitisvisionservice.domain.device.repository.DeviceDataRepository;
import com.vitisvision.vitisvisionservice.domain.device.repository.DeviceRepository;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BlockService class is a service class for block operations.
 */
@Service
@RequiredArgsConstructor
public class BlockService {

    /**
     * Block repository object to perform database operations
     */
    private final BlockRepository blockRepository;

    /**
     * Vineyard service object to perform vineyard operations
     */
    private final VineyardService vineyardService;

    /**
     * Block response mapper object to map the block entity to block response
     */
    private final BlockResponseMapper blockResponseMapper;

    /**
     * Block request mapper object to map the block request to block entity
     */
    private final BlockRequestMapper blockRequestMapper;

    /**
     * Device repository object to perform database operations
     */
    private final DeviceRepository deviceRepository;

    /**
     * Device data repository object to perform database operations
     */
    private final DeviceDataRepository deviceDataRepository;


    /**
     * Create a new block in a vineyard with the provided details
     *
     * @param blockRequest    the request object containing the block details
     * @param vineyardId      the id of the vineyard to which the block belongs
     * @param principal       the principal object containing the user details
     * @return the response object containing the block details
     */
    @PreAuthorize("hasAuthority('block:write')")
    @Transactional
    public BlockResponse createBlock(BlockRequest blockRequest, Integer vineyardId, Principal principal) {
        // Ensure the user is a participant in the vineyard
        vineyardService.ensureVineyardParticipation(vineyardId, principal);

        // Check if the block with the provided name already exists in the vineyard
        if(blockRepository.existsByNameAndVineyardId(blockRequest.getName(), vineyardId)) {
            throw new BlockDuplicationException("block.duplicate.error");
        }

        // Create the block entity object and set the vineyard
        Block createdBlock = blockRequestMapper.apply(blockRequest);
        createdBlock.setVineyard(vineyardService.getVineyardById(vineyardId));

        // Save the block entity object and return the response object
        return blockResponseMapper.apply(blockRepository.save(createdBlock));
    }

    /**
     * Get a block by id in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the block belongs
     * @param blockId    the id of the block to get
     * @param principal  the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('block:read')")
    @Transactional
    public BlockResponse getBlock(Integer vineyardId, Integer blockId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return blockResponseMapper.apply(
                blockRepository.findById(blockId)
                        .orElseThrow(() -> new BlockNotFoundException("block.not.found.error"))
        );
    }

    /**
     * Get all blocks in a vineyard with the provided id
     *
     * @param pageable the pageable object containing the pagination details
     * @param vineyardId the id of the vineyard
     * @param principal the principal object containing the user details
     * @return the page object containing the blocks in the vineyard
     */
    @PreAuthorize("hasAuthority('block:read')")
    @Transactional
    public Page<BlockResponse> getBlocks(Pageable pageable, Integer vineyardId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        return blockRepository.findAllByVineyard_Id(pageable, vineyardId)
                .map(blockResponseMapper);
    }

    /**
     * Update a block in a vineyard with the provided details
     *
     * @param blockRequest the request object containing the block details
     * @param vineyardId   the id of the vineyard to which the block belongs
     * @param blockId      the id of the block to be updated
     * @param principal    the principal object containing the user details
     * @return the response entity containing the response object
     */
    @PreAuthorize("hasAuthority('block:write')")
    @Transactional
    public BlockResponse updateBlock(
            @Valid BlockRequest blockRequest,
            Integer vineyardId, Integer blockId,
            Principal principal
    ) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureBlockExistence(blockId, vineyardId);

        // Check if the block with the provided name already exists in the vineyard, and it is a different block
        Optional<Block> blockByRequestName = blockRepository.findByNameAndVineyard_Id(blockRequest.getName(), vineyardId);
        if (blockByRequestName.isPresent() && !blockByRequestName.get().getId().equals(blockId)) {
            throw new BlockDuplicationException("block.duplicate.error");
        }

        // Update the block entity object
        Block updatedBlock = blockRepository.findById(blockId)
                .orElseThrow(() -> new BlockNotFoundException("block.not.found.error"));
        blockRequestMapper.update(blockRequest, updatedBlock);

        // Save the block entity object and return the response object
        return blockResponseMapper.apply(blockRepository.save(updatedBlock));
    }

    /**
     * Delete a block by id in a vineyard
     *
     * @param vineyardId the id of the vineyard to which the block belongs
     * @param blockId    the id of the block to delete
     * @param principal  the principal object containing the user details
     */
    @PreAuthorize("hasAuthority('block:delete')")
    @Transactional
    public void deleteBlock(Integer vineyardId, Integer blockId, Principal principal) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureBlockExistence(blockId, vineyardId);
        blockRepository.deleteById(blockId);
    }

    /**
     * Generate a block report
     *
     * @param blockReportRequest the request object containing the block report details
     * @param vineyardId         the id of the vineyard to which the block belongs
     * @param blockId            the id of the block to generate the report
     * @param principal          the principal object containing the user details
     * @return the byte array containing the block report
     */
    @PreAuthorize("hasAuthority('block:report')")
    public byte[] generateBlockReport(
            BlockReportRequest blockReportRequest, Integer vineyardId,
            Integer blockId, Principal principal
    ) {
        vineyardService.ensureVineyardParticipation(vineyardId, principal);
        ensureBlockExistence(blockId, vineyardId);

        Instant startDate = LocalDate.parse(blockReportRequest.getStartDate()).atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endDate = LocalDate.parse(blockReportRequest.getEndDate()).atStartOfDay().toInstant(ZoneOffset.UTC);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("start.date.after.end.date.error");
        }

        // Interval in minutes
        int intervalMinutes = Integer.parseInt(String.valueOf(blockReportRequest.getAggregationInterval()));

        List<Integer> deviceDataIds = deviceRepository.findDeviceIdsByBlockId(blockId);
        List<DeviceData> deviceDataList = deviceDataRepository.findAllByDeviceIdsAndTimestampBetween(deviceDataIds, startDate, endDate);

        // Group data by interval
        Map<Instant, List<DeviceData>> dataByInterval = deviceDataList.stream()
                .collect(Collectors.groupingBy(data -> roundDownToInterval(data.getTimestamp(), intervalMinutes)));

        // Calculate averages
        List<Object[]> aggregatedData = dataByInterval.entrySet().stream().map(entry -> {
            Instant intervalStart = entry.getKey();
            Instant intervalEnd = intervalStart.plus(intervalMinutes, ChronoUnit.MINUTES);
            List<DeviceData> dataList = entry.getValue();

            double avgAirTemp = dataList.stream().mapToDouble(DeviceData::getAirTemperature).average().orElse(0);
            double avgAirHumidity = dataList.stream().mapToDouble(DeviceData::getAirHumidity).average().orElse(0);
            double avgGndTemp = dataList.stream().mapToDouble(DeviceData::getGndTemperature).average().orElse(0);
            double avgGndHumidity = dataList.stream().mapToDouble(DeviceData::getGndHumidity).average().orElse(0);
            double avgLux = dataList.stream().mapToDouble(DeviceData::getLux).average().orElse(0);

            return new Object[]{intervalStart, intervalEnd, avgAirTemp, avgAirHumidity, avgGndTemp, avgGndHumidity, avgLux};
        }).collect(Collectors.toList());

        // Generate CSV
        return generateCsv(aggregatedData);
    }

    /**
     * Round down the provided timestamp to the nearest interval
     *
     * @param timestamp        the timestamp to be rounded down
     * @param intervalMinutes  the interval in minutes
     * @return the rounded down timestamp
     */
    private Instant roundDownToInterval(Instant timestamp, int intervalMinutes) {
        // Convert Instant to OffsetDateTime to perform minute manipulations
        OffsetDateTime dateTime = timestamp.atOffset(ZoneOffset.UTC);
        int currentMinute = dateTime.getMinute();
        int roundedMinute = (currentMinute / intervalMinutes) * intervalMinutes;

        // Return adjusted OffsetDateTime to Instant
        return dateTime.withMinute(roundedMinute).withSecond(0).withNano(0).toInstant();
    }

    /**
     * Generate a CSV file from the provided data
     *
     * @param data the list of data to be written to the CSV
     * @return the byte array containing the CSV data
     */
    private byte[] generateCsv(List<Object[]> data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader("Start Interval Time", "End Interval Time", "Avg Air Temperature",
                        "Avg Air Humidity", "Avg Gnd Temperature", "Avg Gnd Humidity", "Avg Lux")
                .build();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
            for (Object[] row : data) {
                csvPrinter.printRecord(row);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV", e);
        }

        return out.toByteArray();
    }

    /**
     * Ensure the user has access to the block with the provided id
     *
     * @param blockId    the id of the block
     * @param principal  the principal object containing the user details
     */
    public void ensureBlockAccess(Integer blockId, Principal principal) {
        Block block = blockRepository.findById(blockId)
                .orElseThrow(() -> new BlockNotFoundException("block.not.found.error"));
        vineyardService.ensureVineyardParticipation(block.getVineyard().getId(), principal);
    }

    /**
     * Get a block by id
     *
     * @param blockId the id of the block
     * @return the block entity object
     */
    public Block getBlockById(Integer blockId) {
        return blockRepository.findById(blockId)
                .orElseThrow(() -> new BlockNotFoundException("block.not.found.error"));
    }

    /**
     * Ensure the existence of a block in a vineyard with the provided id
     *
     * @param blockId    the id of the block
     * @param vineyardId the id of the vineyard
     */
    private void ensureBlockExistence(Integer blockId, Integer vineyardId) {
        if (!blockRepository.existsByIdAndVineyard_Id(blockId, vineyardId)) {
            throw new BlockNotFoundException("block.not.found.error");
        }
    }

}