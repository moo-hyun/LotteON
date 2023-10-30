package co.kr.lotteon.service;

import co.kr.lotteon.dto.LtProductCate1DTO;
import co.kr.lotteon.dto.LtProductCate2DTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.mapper.LtProductMapper;
import co.kr.lotteon.mapper.admin.LtProductCateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class LtAdminService {

    private final LtProductCateMapper ltProductCateMapper;

    private final ModelMapper modelMapper;

    public List<LtProductCate1DTO> selectLtProductCate1s(){
        return ltProductCateMapper.selectLtProductCate1s();
    }

    public List<LtProductCate2DTO> selectLtProductCate2s(String cate1){
        return ltProductCateMapper.selectLtProductCate2s(cate1);
    }


}





