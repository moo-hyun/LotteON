/*휴대폰 번호 자동 하이픈*/
const hypenTel = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
}

window.onload = () => {
    //토탈 값 쉼표 제거
    total = $('input[name=ordTotPrice]').val()
    //total = total.replace(",", "");
    //total = parseInt(total);

    const discountPoint = document.getElementsByClassName('discountPoint')[0]
    const discountPointBut = document.getElementsByClassName('discountPointBut')[0]
    let usePoint = 0;
    const newTotal = document.getElementsByClassName('total')[0]
    discountPointBut.addEventListener('click', (e) => {
        usePoint = parseInt($('input[name=usedPoint]').val());
        //포인트 체크
        if (usePoint < 5000) {
            alert('최소 5,000원 이상의 금액을 입력해주세요.')
        } else if(total-usePoint < 0){
            alert('전체 주문금액 보다 큰 금액 사용은 불가능합니다.')
        }else{
            discountPoint.innerText = '-' + usePoint.toLocaleString()
            console.log(total - usePoint)
            newTotal.innerText = (total - usePoint).toLocaleString()
            $('input[name=ordTotPrice]').val(total - usePoint);
        }
    })


    // 폼 데이터 검증결과 상태변수
    let isRecipNameOk = true;
    let isRecipHpOk = true;
    let isRecipAddrOk = true;
    let isOrdPaymentOk = false;

    // 데이터 검증에 사용하는 정규표현식
    let reName = /^[가-힣]{2,10}$/
    let reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

    // 유효성 검증(Validation)

    // 이름 검사
    $('input[name=recipName]').focusout(function () {
        const name = $(this).val();

        if (name.match(reName)) {
            isRecipNameOk = true;
        } else {
            isRecipNameOk = false;
        }
    });

    // 번호 검사
    $('input[name=recipHp]').keyup(function () {
        const hp = $(this).val();
        if (hp.match(reHp)) {
            isRecipHpOk = true;
        } else {
            isRecipHpOk = false;
        }
    });
    // 주소 검사
    const addr = document.getElementsByName('recipAddr2');
    addr[0].focusout = function () {
        const addr = document.getElementsByName('recipAddr1');
        if (addr[0].value == '') {
            isRecipAddrOk = false;
        } else {
            isRecipAddrOk = true;
        }
    }
    // 결제수단 검사
    $('input[name=ordPayment]').click(function () {
        isOrdPaymentOk = true;
    });


    const progressOrder = document.getElementsByName('progressOrder')[0]
    progressOrder.addEventListener('click', (e) => {

        if (!isRecipNameOk) {
            alert('수령인을 확인 하십시오.');
            return false; // 폼 전송 취소
        }


        if (!isRecipHpOk) {
            alert('전화번호를 확인 하십시오.');
            return false; // 폼 전송 취소
        }
        if (!isRecipAddrOk) {
            alert('주소를 확인 하십시오.');
            return false; // 폼 전송 취소
        }

        if (!isOrdPaymentOk) {
            alert('결제수단을 확인 하십시오.');
            return false; // 폼 전송 취소
        }



        if (!confirm('결제하시겠습니까?')) {
            e.preventDefault();
            alert('주문을 완료하기 위해 결제를 진행해주세요.');
        } else {
            alert('결제 진행중...');
            if(prompt('결제를 완료했을 시 [결제완료]라고 입력해주세요.') == '결제완료'){
                alert('결제가 완료되었습니다!')
                document.getElementById('orderForm').submit();
            } else{
                alert('결제에 오류가 생겼습니다.\n주문을 완료하기 위해 결제를 진행해주세요.');
            }
        }
    })


}