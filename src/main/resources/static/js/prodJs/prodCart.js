$(function() {

    let success =  new URL(location.href).searchParams.get('success');

    if(success ==200){
        alert('성공적으로 삭제되었습니다.');
    }

    $('input[name=chk]').attr('checked', true);
    $('input[name=all]').attr('checked', true);
    setTotalFormVal();

    // 전체 체크박스
    $('input[name=all]').change(function(){
        const isChecked = $(this).is(':checked');
        if (isChecked) {
            $('input[name=chk]').prop('checked', true);
            setTotalFormVal();
        } else {
            $('input[name=chk]').prop('checked', false);
            $('.totalForm').text(0);
        }
    });

    $('input[name=chk]').change(function() {
        setTotalFormVal();
    });

    // 삭제
    $('#cartDelete').click(function(){
        if (confirm('해당 장바구니 품목을 삭제 하시겠습니까?')) {
            const chk = $('input[name=chk]');
            let arr = [];
            for (let i = 0; i < chk.length; i++) {
                if (chk[i].checked) {
                    const cartNo = $('input[name=chk]')[i].classList[0];
                    arr.push(cartNo);
                }
            }
            const jsonData = JSON.stringify(arr);
            window.location.href = '/LotteON/product/cartDelete?jsonData=' + encodeURIComponent(jsonData);
        }
    });

    // 주문
    $('#formOrder').submit(function(e) {
        if ($('#total').text() == 0) {
            alert('주문을 진행할 상품이 선택되지 않았습니다..');
            return false;
        }
        return true;
    });



    function setTotalFormVal(){
        const chk = $('input[name=chk]');
        let cnt = 0;
        let count = 0;
        let price = 0;
        let disPrice = 0;
        let delivery = 0;
        let point = 0;
        let total = 0;
        for (let i = 0; i < chk.length; i++) {
            if (chk[i].checked) {
                const cartNo = parseInt($('input[name=chk]')[i].classList[0]);
                let cartCnt = parseInt($('input[name=count' + cartNo + ']').val());
                let cartPrice = parseInt($('input[name=price' + cartNo + ']').val()) * cartCnt;
                let cartDisPrice = parseInt($('input[name=disPrice' + cartNo + ']').val()) * cartCnt;
                let cartDelivery = parseInt($('input[name=delivery' + cartNo + ']').val());
                let cartPoint = parseInt($('input[name=point' + cartNo + ']').val()) * cartCnt;
                let cartTotal = cartPrice - cartDisPrice + cartDelivery;
                console.log(cartPrice + "- " + cartDisPrice + " +  " + cartDelivery);

                count += cartCnt;
                price += cartPrice;
                disPrice += cartDisPrice;
                delivery += cartDelivery;
                point += cartPoint;
                total += cartTotal;
                cnt++;
            }
        }
        $('#count').text(count.toLocaleString());
        $('#price').text(price.toLocaleString());
        $('#disPrice').text(disPrice.toLocaleString());
        $('#delivery').text(delivery.toLocaleString());
        $('#point').text(point.toLocaleString());
        $('#total').text(total.toLocaleString());

        $('input[name=totalCount]').val(count);
        $('input[name=totalPrice]').val(price);
        $('input[name=totalDiscount]').val(disPrice);
        $('input[name=totalDelivery]').val(delivery);
        $('input[name=totalPoint]').val(point);
        $('input[name=totalSumPrice]').val(total);

        if (!$(this).is(':checked')) {
            $('input[name=all]').prop('checked', false);
        } else if (cnt == chk.length) {
            $('input[name=all]').prop('checked', true);
        }
    }
});