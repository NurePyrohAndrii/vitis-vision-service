package com.vitisvision.vitisvisionservice.common.util;

import com.github.javafaker.Faker;
import com.vitisvision.vitisvisionservice.domain.block.dto.BlockRequest;
import com.vitisvision.vitisvisionservice.domain.block.service.BlockService;
import com.vitisvision.vitisvisionservice.domain.vinayard.dto.VineyardRequest;
import com.vitisvision.vitisvisionservice.domain.vinayard.service.VineyardService;
import com.vitisvision.vitisvisionservice.domain.vine.dto.VineRequest;
import com.vitisvision.vitisvisionservice.domain.vine.service.VineService;
import com.vitisvision.vitisvisionservice.security.dto.RegisterRequest;
import com.vitisvision.vitisvisionservice.security.service.AuthService;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * This class is used to seed the database with some initial data.
 * It creates some vineyard directors, vineyards, blocks and vines.
 */
@Service
@RequiredArgsConstructor
public class StartupSeeder {

    /**
     * The number of users and their vineyards to be created.
     */
    private static final int VINEYARD_DIRECTORS_COUNT = 5;

    /**
     * The number of blocks to be created for each vineyard.
     */
    private static final int BLOCK_COUNT = 3;

    /**
     * The number of rows in block.
     */
    private static final int ROW_COUNT = 3;

    /**
     * The number of vines to be created for each row in block.
     */
    private static final int VINE_COUNT_PER_ROW = 10;

    /**
     * The services to be used in the seeder.
     */
    private final AuthService authService;
    private final VineyardService vineyardService;
    private final BlockService blockService;
    private final VineService vineService;
    private final Faker faker = new Faker();

    /**
     * This method seeds the database with some initial data.
     */
    public void run() {
        int blockIndex = 1;
        for (int i = 1; i <= VINEYARD_DIRECTORS_COUNT; i++) {
            Principal authentication = registerUserAndCreateVineyard(i);
            for (int j = 1; j <= BLOCK_COUNT; j++) {
                blockService.createBlock(getBlockRequest(blockIndex), i, authentication);
                filBlockWithVines(authentication, blockIndex);
                blockIndex++;
            }
        }
    }

    /**
     * This method registers a user and creates a vineyard for the user.
     * @param i the index of the user
     * @return the authentication of the user
     */
    private Principal registerUserAndCreateVineyard(int i) {
        User user = registerUser(i);
        UsernamePasswordAuthenticationToken authentication = getAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(user));

        VineyardRequest vineyardRequest = getVineyardRequest();
        vineyardService.createVineyard(vineyardRequest, authentication);
        user.setRole(Role.VINEYARD_DIRECTOR);

        authentication = getAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    /**
     * This method registers a user.
     * @param i the index of the user
     * @return the registered user
     */
    private User registerUser(int i) {
        RegisterRequest registerRequest = getRegisterRequest(i);
        authService.register(registerRequest);
        return getUser(registerRequest);
    }

    /**
     * This method fills a block with vines.
     * @param authentication the authentication of the user
     * @param blockIndex the index of the block
     */
    private void filBlockWithVines(Principal authentication, int blockIndex) {
        for (int rowNum = 1; rowNum <= ROW_COUNT; rowNum++) {
            for (int vineNum = 1; vineNum <= VINE_COUNT_PER_ROW; vineNum++) {
                vineService.createVine(
                        getVineRequest(rowNum, vineNum),
                        blockIndex,
                        authentication);
            }
        }
    }

    /**
     * This method creates a user from the register request.
     * @param registerRequest the register request
     * @return the created user
     */
    private User getUser(RegisterRequest registerRequest) {
        return User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(registerRequest.getPassword())
                .role(Role.USER)
                .build();
    }

    /**
     * This method creates a register request.
     * @param i the index of the register request
     * @return the created register request
     */
    private RegisterRequest getRegisterRequest(int i) {
        return RegisterRequest.builder()
                .email(faker.internet().emailAddress())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .password("Password1$" + i)
                .build();
    }

    /**
     * This method creates a vineyard request.
     * @return the created vineyard request
     */
    private VineyardRequest getVineyardRequest() {
        String dbaName = faker.company().name();
        return VineyardRequest.builder()
                .companyName(faker.company().name())
                .dbaName(dbaName)
                .streetAddress(faker.address().streetAddress())
                .city(faker.address().city())
                .zipCode(faker.address().zipCode())
                .phoneNumber(faker.phoneNumber().cellPhone())
                .email(dbaName.toLowerCase().replace(" ", "") + "@vineyard.com")
                .build();
    }

    /**
     * This method creates a block request.
     * @param blockIndex the index of the block
     * @return the created block request
     */
    private BlockRequest getBlockRequest(Integer blockIndex) {
        return BlockRequest.builder()
                .name("Block " + blockIndex)
                .partitioningType("PartitioningType " + blockIndex)
                .rowSpacing(300 + blockIndex)
                .vineSpacing(100 + blockIndex)
                .rowOrientation(faker.lorem().word())
                .trellisSystemType(faker.lorem().word())
                .build();
    }

    /**
     * This method creates a vine request.
     * @param rowNumber the number of the row
     * @param vineNumber the number of the vine
     * @return the created vine request
     */
    private VineRequest getVineRequest(Integer rowNumber, Integer vineNumber) {
        return VineRequest.builder()
                .vineNumber(vineNumber)
                .rowNumber(rowNumber)
                .variety(faker.lorem().word())
                .bolesCount(1)
                // as yyyy-MM-dd
                .plantingDate(faker.date()
                        .birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .formationType(faker.lorem().word())
                .build();
    }

    /**
     * This method creates an authentication token for the user.
     * @param user the user
     * @return the created authentication token
     */
    private UsernamePasswordAuthenticationToken getAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
