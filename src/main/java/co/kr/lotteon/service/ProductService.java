package co.kr.lotteon.service;

import co.kr.lotteon.dto.LtCsNoticeDTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.dto.LtProductReviewDTO;
import co.kr.lotteon.dto.cspage.CsPageRequestDTO;
import co.kr.lotteon.dto.cspage.CsPageResponseDTO;
import co.kr.lotteon.dto.mypage.MyPageRequestDTO;
import co.kr.lotteon.dto.mypage.MyPageResponseDTO;
import co.kr.lotteon.dto.prodpage.ProdPageRequestDTO;
import co.kr.lotteon.dto.prodpage.ProdPageResponseDTO;
import co.kr.lotteon.entity.LtCsNoticeEntity;
import co.kr.lotteon.entity.LtProductEntity;
import co.kr.lotteon.entity.LtProductReviewEntity;
import co.kr.lotteon.mapper.LtProductMapper;
import co.kr.lotteon.repository.LtProductRepository;
import co.kr.lotteon.repository.LtProductReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final LtProductRepository ltProductRepository;
    private final LtProductReviewRepository ltProductReviewRepository;


    // 카테고리별 리스트
    public ProdPageResponseDTO getProductListByCates(ProdPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("prodNo");

        Page<LtProductEntity> result = ltProductRepository.findAllByProdCate1AndProdCate2(pageRequestDTO.getCate1(), pageRequestDTO.getCate2(), pageable);
        List<LtProductDTO> dtoList = result.getContent()
                .stream()
                .map(
                        LtProductEntity::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return ProdPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

    public ProdPageResponseDTO getProductList(ProdPageRequestDTO pageRequestDTO) {

        // 정렬기준, 정렬
        Pageable pageable = null;
        Page<LtProductEntity> result = null;
        if(pageRequestDTO.getIsCategory().equals("Y")){
            //카테고리별
            pageable=pageRequestDTO.getPageable(pageRequestDTO.getSort(), pageRequestDTO.getHow());
            result = ltProductRepository.findAllByProdCate1AndProdCate2(pageRequestDTO.getCate1(), pageRequestDTO.getCate2(), pageable);
        } else if (pageRequestDTO.getIsCategory().equals("sold")){
            pageable = pageRequestDTO.getPageable("sold", "DESC");
            result = ltProductRepository.findAllByOrderBySoldDesc(pageable);
        }else if(pageRequestDTO.getIsCategory().equals("price_low")){
            pageable = pageRequestDTO.getPageable("price", "ASC");
            result = ltProductRepository.findAllByOrderByPriceAsc(pageable);
        }else if(pageRequestDTO.getIsCategory().equals("price_high")){
            pageable = pageRequestDTO.getPageable("price", "DESC");
            result = ltProductRepository.findAllByOrderByPriceDesc(pageable);
        }else if(pageRequestDTO.getIsCategory().equals("review_high")){
            pageable = pageRequestDTO.getPageable("review", "DESC");
            result = ltProductRepository.findAllByOrderByReviewDesc(pageable);
        }else if(pageRequestDTO.getIsCategory().equals("score_high")){
            pageable = pageRequestDTO.getPageable("score", "DESC");
            result = ltProductRepository.findAllByOrderByScoreDesc(pageable);
        }else if(pageRequestDTO.getIsCategory().equals("recent")){
            pageable = pageRequestDTO.getPageable("prodNo", "DESC");
            result = ltProductRepository.findAllByOrderByProdNoDesc(pageable);
        }else if(pageRequestDTO.getIsCategory().equals("search")){
            pageable = pageRequestDTO.getPageable("prodNo", "DESC");
            result = ltProductRepository.findAllByProdNameContainsOrderByProdNoDesc(pageRequestDTO.getSearch() ,pageable);
        }
        List<LtProductDTO> dtoList = result.getContent()
                .stream()
                .map(
                        LtProductEntity::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return ProdPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }
    public MyPageResponseDTO getReviews(int prodNo, MyPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("rdate", 10);





        Page<LtProductReviewEntity> result = null;
        result = ltProductReviewRepository.findAllByLtProductEntity(LtProductEntity.builder().prodNo(prodNo).build(), pageable);
        List<LtProductReviewDTO> dtoList = result
                .stream().map(LtProductReviewEntity::toDTO).toList();

        int totalElement = (int) result.getTotalElements();
        return MyPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .reviewList(dtoList)
                .total(totalElement)
                .build();
    }
}
