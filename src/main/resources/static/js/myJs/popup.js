$(function() {

    let selectedA;
    let success =  new URL(location.href).searchParams.get('success');

    if(success ==200){
        alert('성공적으로 리뷰가 작성되었습니다.');
    }

    // 판매자 정보 팝업 띄우기
    $('.orderItem .company > a').click(function(e){
        e.preventDefault();
        $('#popSeller').addClass('on');
    });

    // 문의하기 팝업 띄우기
    $('.btnQuestion').click(function(e){
        e.preventDefault();
        $('.popup').removeClass('on');
        $('#popQuestion').addClass('on');
    });

    // 주문상세 팝업 띄우기
    $('.orderInfo .orderNo > a').click(function(e){
        $(this).next().show();
        e.preventDefault();

        const tr 		             = $(this).parent().parent(); // .orderInfo
        const ordNo	         = tr.find('.orderNo').find('.ordNo').text();
        const date 		     = tr.find('.date').text();
        const status 		 = tr.find('.status').text();
        const ordName 		 = tr.find('.ordName').text();
        const recipName 		 = tr.find('.recipName').text();
        const recipHp 		 = tr.find('.recipHp').text();
        const recipAddress 		 = tr.find('.recipAddress').text();

        let company = [];
        let prodName = [];
        let prodCount = [];
        let price = [];
        let priceNum = [];
        let discount = [];
        let total = [];
        let thumb = [];


        let selectedTR = tr.next(); //.orderItem
        while(true){
            if(selectedTR.attr('class') !== 'orderItem'){
                break;
            }

            company.push(selectedTR.find('.company').text());
            prodName.push(selectedTR.find('.prodName').find('a').text());
            prodCount.push(selectedTR.find('.prodCount').text());
            price.push(selectedTR.find('.prodPriceIndiv').text());
            discount.push(selectedTR.find('.discount').text());
            total.push(selectedTR.find('.total').text());
            thumb.push(selectedTR.find('.thumb').find('img').attr('src'));
            priceNum.push(selectedTR.find('.priceNum').text());


            //console.log(selectedTR.find('.thumb').find('img').attr('src'));
            // 다음 item 선택, item이 아닐시 반복문 끝
            selectedTR = selectedTR.next();
        }

        const popup = $('#popOrder');
        popup.find('.ordNo').text(ordNo);
        popup.find('.date').text(date);
        popup.find('.status').text(status);
        popup.find('.ordName').text(ordName);
        popup.find('.recipName').text(recipName);
        popup.find('.recipHp').text(recipHp);
        popup.find('.recipAddress').text(recipAddress);

        const popOrdItem = popup.find('.popOrdItem');
        popOrdItem.empty();

        let htmlItem = '';
        for(let i=0; i<company.length; i++){

            htmlItem += '<tr>';

            //주문번호
            htmlItem += '<td>';
            htmlItem += '<p class="date">' + date + '</p>';
            htmlItem += '<span class="ordNo">' + ordNo + '</span>';
            htmlItem += '</td>';
            //상품 정보
            htmlItem += '<td>';
            htmlItem += '<img class="thumb" src="' + thumb[i] +'" alt="thumb">';
            htmlItem += '<ul>';
            htmlItem += '<li class="company">' + company[i] +'</li>';
            htmlItem += '<li class="prodName">' + prodName[i] +'</li>';
            htmlItem += '<li> 수량 : <span class="prodCount">' + prodCount[i] +'</span>개</li>';
            htmlItem += '<li class="prodPrice">' + price[i] +'</li>';
            htmlItem += '</ul>';
            htmlItem += '</td>';
            //결제정보
            htmlItem += '<td class="payment">';
            htmlItem += '<ol>';
            htmlItem += '<li class="price"><span>판매가(개당)</span><span>'+ price[i] +'원</span></li>';
            htmlItem += '<li class="discount"><span>할인(개당)</span><span>'+ discount[i] +'원</span></li>';
            htmlItem += '<li class="total"><span>결제금액</span><span>총 '+ total[i] +'원</span></li>';
            htmlItem += '</ol>';
            htmlItem += '</td>';
            //주문상태
            htmlItem += '<td class="status">' + status + '</td>';

            htmlItem += '</tr>';

        }
        popOrdItem.html(htmlItem);

        popup.addClass('on');
    });

    // 수취확인 팝업 띄우기
    $('.orderItem .confirm > .receive').click(function(e){
        e.preventDefault();
        selectedA = $(this);
        const ordNo 		     = $(this).parent().find('.ordNo').text();

        $('#popReceive').find('.ordNo').text(ordNo);
        $('#popReceive').addClass('on');
    });
    $('#popReceive .btnPositive').click(function (e){
        const ordNo = $(this).parent().find('.ordNo').text();
        //const uid = $('input[name=userID]').val();
        $.ajax({
            url: '/LotteON/my/api/delivery/' + ordNo,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log(data);

                alert('성공적으로 수취확인 처리 되었습니다.')
                document.getElementById('popReceive').className = 'popup';

                selectedA.parent().parent().parent().find('.status').each(
                    function(){
                        $(this).find('span').text('수취확인');
                        $(this).find('span').attr('class','ordComplete-t5');
                    }
                );
                selectedA.hide();

            }
        });
    });

    $('#popReceive .btnCancel').click(function(){
        $(this).closest('.popup').removeClass('on');
    });

    // 상품평 작성 팝업 띄우기
    $('.orderItem .confirm > .review').click(function(e){
        e.preventDefault();

        selectedA = $(this);
        const ordNo 		     = $(this).parent().find('.ordNo').text();
        const prodNo 		     = $(this).parent().find('.prodNo').text();
        const prodName 		     = $(this).parent().find('.prodName').text();

        $('#popReview').find('input[name=ordNo]').val(ordNo);
        $('#popReview').find('input[name=prodNo]').val(prodNo);
        $('#popReview').find('.productName').text(prodName);

        $('#popReview').addClass('on');
    });

    // 상품평 작성 submit
    $('#popReview .btnPositive').click(function(e){
        e.preventDefault();

        $('#popReview').removeClass('on');
        $('#popReview #reviewForm').submit();

    });
               
    // 팝업 닫기
    $('.btnClose').click(function(){                
        $(this).closest('.popup').removeClass('on');                
    });

    // 상품평 작성 레이팅바 기능
    $(".my-rating").starRating({
        starSize: 20,
        useFullStars: true,
        strokeWidth: 0,
        useGradient: false,
        minRating: 1,
        ratedColors: ['#ffa400', '#ffa400', '#ffa400', '#ffa400', '#ffa400'],
        callback: function(currentRating, $el){

            $('#popReview').find('input[name=rating]').val(currentRating);
            //alert('rated ' + currentRating);
            //console.log('DOM element ', $el);

            /*

            $.ajax({
                url: '/LotteON/my/api/review/',
                data : {
                  ordNo: ,
                  rating  : currentRating
                },
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    console.log(data);

                    alert('성공적으로 수취확인 처리 되었습니다.')
                    document.getElementById('popReceive').className = 'popup';

                    selectedA.parent().parent().parent().find('.status').each(
                        function(){
                            $(this).find('span').text('수취확인');
                            $(this).find('span').attr('class','ordComplete-t5');
                        }
                    );
                    selectedA.hide();

                }
            });
            */

        }
    });

});