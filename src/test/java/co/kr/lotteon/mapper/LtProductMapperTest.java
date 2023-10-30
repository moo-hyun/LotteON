package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.service.LtProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LtProductMapperTest {


    @Autowired
    private LtProductMapper ltProductMapper;

    @Autowired
    private LtProductService ltProductService;

    @Test
    public void deleteLtProduct(){
        //ltProductMapper.deleteLtProduct(16382);

        LtProductDTO ltProductDTO = LtProductDTO.builder()
                                            .prodNo(16381)
                                            .prodCate1(10)
                                            .prodCate2(10)
                                            .thumb1("7a2234ee-90ea-4da7-af47-b173a8f1b540.jpg")
                                            .thumb2("eee14c3a-a163-4ae6-bbcb-afe3fe26afe9.jpg")
                                            .thumb3("9c0a42e3-2b6a-43d7-a521-580b43a9671a.jpg")
                                            .detail("3dc34433-a1e4-4e21-978e-80bf15526555.jpg").build();

        ltProductService.deleteLtProduct(ltProductDTO);


    }


}
