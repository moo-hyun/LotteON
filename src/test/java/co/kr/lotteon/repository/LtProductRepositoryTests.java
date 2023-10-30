package co.kr.lotteon.repository;


import co.kr.lotteon.dto.prodpage.ProdPageRequestDTO;
import co.kr.lotteon.entity.LtProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class LtProductRepositoryTests {

    @Autowired
    private LtProductRepository ltProductRepository;



 /*   public void findALLByIsRemovedAndProdNoContainsTest(){

        ProdPageRequestDTO prodPageRequestDTO = ProdPageRequestDTO.builder().build();
        Pageable pageable = prodPageRequestDTO.getPageable("cate1");

        Page<LtProductEntity> result =  ltProductRepository.findAllByIsRemovedAndProdNoContains(0, 3, pageable);

        System.out.println("result : " + result);


    }*/

}
