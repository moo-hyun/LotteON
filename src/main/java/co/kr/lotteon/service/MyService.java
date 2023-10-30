package co.kr.lotteon.service;

import co.kr.lotteon.dto.*;
import co.kr.lotteon.dto.mypage.*;
import co.kr.lotteon.entity.*;
import co.kr.lotteon.mapper.my.LtMyMapper;
import co.kr.lotteon.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
@RequiredArgsConstructor
public class MyService {

    private final LtProductRepository ltProductRepository;
    private final LtProductCartRepository ltProductCartRepository;
    private final LtProductOrderRepository ltProductOrderRepository;
    private final LtProductOrderItemRepository ltProductOrderItemRepository;
    private final LtMemberRepository ltMemberRepository;
    private final LtMemberPointRepository ltMemberPointRepository;
    private final LtProductReviewRepository ltProductReviewRepository;
    private final ModelMapper modelMapper;
    private final LtCsQnaRepository ltCsQnaRepository;


    public MyPageResponseDTO showPoint(MyPageRequestDTO pageRequestDTO, String uid){
        Pageable pageable = pageRequestDTO.getPageable("pointNo", 10);
        Page<LtMemberPointEntity> result = null;
        result = ltMemberPointRepository.findAllByUid(uid, pageable);
        List<LtMemberPointDTO> dtoList = result.getContent()
                .stream().
                map(
                        LtMemberPointEntity::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return MyPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .pointList(dtoList)
                .total(totalElement)
                .build();
    }

    public MyPageResponseDTO showOrder(MyPageRequestDTO pageRequestDTO, String ordUid){
        Pageable pageable = pageRequestDTO.getPageable("ordNo", 5);
        Page<LtProductOrderEntity> result = null;
        result = ltProductOrderRepository.findAllByOrdUid(ordUid, pageable);
        List<MyOrdersDTO> myOrdersDTOList = new ArrayList<>();
        List<LtProductOrderDTO> orderList = result.getContent()
                .stream().
                map(
                       entity -> modelMapper.map(entity, LtProductOrderDTO.class)
                )
                .toList();
        for(LtProductOrderDTO dto : orderList){
            log.info("myOrder DTO : " + dto);
            List<LtProductOrderItemDTO> itemList = ltProductOrderItemRepository.findAllByLtProductOrderItemPK_OrdNo(dto.getOrdNo())
                    .stream()
                    .map(LtProductOrderItemEntity::toDTO)
                    .toList();
            MyOrdersDTO myOrders = new MyOrdersDTO(dto, itemList);
            myOrdersDTOList.add(myOrders);
            log.info("myOrders CLASS : " + myOrders);
        }
        int totalElement = (int) result.getTotalElements();
        return MyPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .myOrdersDTOList(myOrdersDTOList)
                .total(totalElement)
                .build();
    }

    public int deliveryComplete(int ordNo){
        ltProductOrderRepository.updateOrdComplete(ordNo, 5);
        return 1;
    }

    public void writeReview(LtProductReviewDTO dto, String uid){
        int revNo = ltProductReviewRepository.save(dto.toEntity()).getRevNo();
        //productItem - hasReview 업데이트
        ltProductOrderItemRepository.updateOrdComplete(dto.getOrdNo(), dto.getProdNo(), revNo );
        // point 추가 - 2000
        int point = 2000;
        ltMemberRepository.updateMemberPointPlus(uid, point);
        LtMemberPointEntity savedPointEntity = LtMemberPointEntity.builder()
                .uid(uid)
                .revNo(revNo)
                .point(point)
                .build();
        ltMemberPointRepository.save(savedPointEntity);
        //평점 업데이트
        int avgScore = ltProductReviewRepository.findAVG(dto.getProdNo());
        log.info("avgScore : " + avgScore + "/prodNo : " + dto.getProdNo());
        ltProductRepository.updateScore(dto.getProdNo(), avgScore);

        //수취확인
        ltProductOrderRepository.updateOrdComplete(dto.getOrdNo(), 5);
    }

    public MyPageResponseDTO showReview(MyPageRequestDTO pageRequestDTO, String uid){
        Pageable pageable = pageRequestDTO.getPageable("revNo", 10);
        Page<LtProductReviewEntity> result = null;
        result = ltProductReviewRepository.findAllByUid(uid, pageable);
        List<LtProductReviewDTO> dtoList = result.getContent()
                .stream().
                map(
                       LtProductReviewEntity::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return MyPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .reviewList(dtoList)
                .total(totalElement)
                .build();
    }
    public MyMenuDTO getMyMenu(String uid){
        int point = ltMemberRepository.findPointByUid(uid).getPoint();
        int myQnaCount = ltCsQnaRepository.countByWriter(uid);
        int orderNowCount = ltProductOrderRepository.countByOrdUidAndOrdComplete(uid, 1);
        return MyMenuDTO.builder()
                .point(point)
                .myQnaCount(myQnaCount)
                .orderNowCount(orderNowCount)
                .build();
    }

    public MyHomeDTO getMyHomeInfo(String uid){
        LtProductOrderDTO order = modelMapper.map(ltProductOrderRepository.findTop1ByOrdUidOrderByOrdDateDesc(uid), LtProductOrderDTO.class);
        List<LtProductOrderItemDTO> orderList = ltProductOrderItemRepository.findAllByLtProductOrderItemPK_OrdNo(order.getOrdNo()).stream().map(LtProductOrderItemEntity::toDTO).toList();
        LtMemberPointDTO point = ltMemberPointRepository.findTop1ByUidOrderByPointDateDesc(uid).toDTO();
        List<LtProductReviewDTO> reviewList = ltProductReviewRepository.findTop5ByUidOrderByRdateDesc(uid).stream().map(LtProductReviewEntity::toDTO).toList();
        List<LtCsQnaDTO> qnaList = ltCsQnaRepository.findTop3ByWriterOrderByRdate(uid).stream().map(entity -> modelMapper.map(entity, LtCsQnaDTO.class)).toList();
        LtMemberDTO member = ltMemberRepository.findByUid(uid).toDTO();
        return MyHomeDTO.builder()
                .order(order)
                .orderList(orderList)
                .point(point)
                .reviewList(reviewList)
                .qnaList(qnaList)
                .member(member)
                .build();
    }
}
