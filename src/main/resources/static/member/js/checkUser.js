/**
 * 사용자 개인정보 중복체크
 *
 * 이름은 중복될 수 있기 때문에 중복체크 안 하고 validation에서 유효성 검사만 한다.
 */
$(function(){

    // 아이디 중복체크
    $('input[name=uid]').focusout(function(){

        //alert('focusout!');

        const uid = $('input[name=uid]').val();

        if(!uid.match(reUid)){
            $('.msgId').css('color', 'red').text('유효한 아이디가 아닙니다.');
            isUidOk = false;
            return; // 종료
        }

        $.ajax({
            url:'/member/check/uid/' + uid,
            type:'GET',
            dataType:'json',
            success:function(data){
                if(data.result >= 1){
                    $('.msgId').css('color', 'red').text('이미 사용중인 아이디 입니다.');
                    isUidOk = false;
                }else{
                    $('.msgId').css('color', 'green').text('사용 가능한 아이디 입니다.');
                    isUidOk = true;
                }
            }
        });

    }); // 아이디 중복체크 끝


    // 이메일 중복체크
    document.getElementsByName('email')[0].onfocusout = function(){

        //alert('focusout!');

        const msgEmail = document.getElementsByClassName('msgEmail')[0];

        // 입력 데이터 가져오기
        const email = this.value;

        if(!email.match(reEmail)){
            msgEmail.innerText = '유효한 이메일이 아닙니다.';
            msgEmail.style.color = 'red';
            isEmailOk = false;
            return;
        }

        // 데이터 전송
        const xhr = new XMLHttpRequest();
        xhr.open('GET', '/member/check/email/'+email);
        xhr.send();

        // 응답 결과
        xhr.onreadystatechange = function(){
            if(xhr.readyState == XMLHttpRequest.DONE){
                if(xhr.status == 200){
                    const data = JSON.parse(xhr.response);
                    console.log('data : ' + data);

                    if(data.result >= 1){
                        msgEmail.innerText = '이미 사용중인 이메일 입니다.';
                        msgEmail.style.color = 'red';
                        isEmailOk = false;
                    }else{
                        msgEmail.innerText = '사용 가능한 이메일 입니다.';
                        msgEmail.style.color = 'green';
                        isEmailOk = true;
                    }
                }
            }
        }// onreadystatechange end
    } // 이메일 중복체크 끝

    // 휴대폰 중복체크
    document.getElementsByName('hp')[0].addEventListener('focusout', function(){

        //alert('focusout!');

        const msgHp = document.getElementsByClassName('msgHp')[0];
        const hp = this.value;

        if(!hp.match(reHp)){
            msgHp.innerText = '유효한 휴대폰번호가 아닙니다.';
            msgHp.style.color = 'red';
            isHpOk = false;
            return;
        }

        const url = '/member/check/hp/'+hp;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log(data);

                if(data.result >= 1){
                    msgHp.innerText = '이미 사용중인 휴대폰번호 입니다.';
                    msgHp.style.color = 'red';
                    isHpOk = false;
                }else{
                    msgHp.innerText = '사용 가능한 휴대폰번호 입니다.';
                    msgHp.style.color = 'green';
                    isHpOk = true;
                }
            });

    }); // 휴대폰 중복체크 끝

}); // 사용자 개인정보 중복체크 끝