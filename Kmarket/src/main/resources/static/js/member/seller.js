let reUid = /^[a-zA-z0-9]{4,12}$/;
let rePass = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,12}$/;
let reBizReg = /^[0-9]{3}-[0-9]{2}-[0-9]{5}$/;
let reEmailReg = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
let reName  = /^[가-힣]+$/
let reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

let isUidOk = false;
let isPassOk = false;
let isBizOk = false;
let isEmailOk = false;
let isNameOk  = false;
let isHpOk 	  = false;

$(function(){
    // 아이디 검사하기
    $('input[name=uid]').keydown(function(){
        isUidOk = false;
    });
    /* ---- 중복체크 ---- */
    $('#uid').focusout(function(){

        let uid = $('input[name=uid]').val();

        if(isUidOk){
            return;
        }

        if(!uid.match(reUid)) {
            $('.msgId').hide();
            $('.uidResult').css('color', 'red').text('유효한 아이디가 아닙니다.');
            isUidOk = false;
            return;
        }

        let jsonData = {
            "uid": uid
        };

        $('.uidResult').css('color', 'black').text('...');

        setTimeout(function(){

            $.ajax({
                url: '/Kmarket/member/uidCheck',
                method: 'get',
                data: jsonData,
                dataType: 'json',
                success: function(data){
                    console.log(data)
                    if(data.result == 0){
                        $('.msgId').hide();
                        $('.uidResult').css('color', 'green').text('사용 가능한 아이디 입니다.');
                        isUidOk = true;
                    }else{
                        $('.uidResult').css('color', 'red').text('이미 사용중인 아이디 입니다.');
                        isUidOk = false;
                    }
                }
            });

        }, 500);
    });
    /* ------- 중복 체크 ------ */

    // 비밀번호 검사하기
    $('input[name=pass1]').focusout(function(){
        let pass1 = $('input[name=pass1]').val();

        if(pass1.match(rePass)){
            $('.msgPass1').hide();
            $('.passResult1').text('')
        }else{
            isPassOk = false;
            $('.msgPass1').hide();
            $('.passResult1').css('color', 'red').text('숫자,영문,특수문자 포함 8자리 이상 이어야 합니다.');
        }
    })

    $('input[name=pass2]').focusout(function(){

        let pass1 = $('input[name=pass1]').val();
        let pass2 = $('input[name=pass2]').val();
        if(pass1 == pass2){
            isPassOk = true;
            $('.msgPass2').hide();
            $('.passResult2').css('color', 'green').text('사용하실 수 있는 비밀번호 입니다.');
        }else{
            isPassOk = false;
            $('.msgPass2').hide();
            $('.passResult2').css('color', 'red').text('비밀번호가 일치하지 않습니다.');
        }
    });

    // 사업자등록번호 검사
    $('input[name=bizRegNum]').focusout(function(){
        let biz = $(this).val();

        if(isBizOk){
            return;
        }

        if(!biz.match(reBizReg)){
            $('.msgCorp').css('color', 'red').text('양식에 맞게 입력바랍니다.');
        }else{
            $('.msgCorp').css('color', 'green').text('사업자 등록번호가 확인되었습니다.');
        }
    });

    // 이메일 검사
    $('input[name=email]').focusout(function(){
        let email = $(this).val();

        if(isEmailOk){
            return;
        }

        if(!email.match(reEmailReg)){
            $('.msgEmail').css('color', 'red').text('이메일 형식에 맞게 입력바랍니다.');
        }else{
            $('.msgEmail').css('color', 'green').text('사용 가능한 이메일입니다.');
        }
    });

    $('input[name=name]').focusout(function(){
        let name = $(this).val();

        if(name.match(reName)){
            isNameOk = true;
            $('.msgName').text('');
        }else{
            isNameOk = false;
            $('.nameResult').css('color', 'red').text('유효한 이름이 아닙니다.');
        }
    });

})