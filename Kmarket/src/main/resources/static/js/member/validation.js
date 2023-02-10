/*
 	날짜 : 2022/10/21
 	이름 : 조광호
 	내용 : 사용자 회원가입 유효성 검사
*/

    // 데이터 검증에 사용하는 정규표현식
    let reUid   = /^[a-z]+[a-z0-9]{4,12}$/g;
    let rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,12}$/;
    let reName  = /^[가-힣]+$/
    let reNick  = /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
    let reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    let reHp    = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

    // 폼 데이터 검증 결과 상태변수
    let isUidOk   = false;
    let isPassOk  = false;
    let isNameOk  = false;
    let isNickOk  = false;
    let isEmailOk = false;
    let isEmailAuthOk = false;
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
        /* ---- 비밀번호 ---- */

        // 이름 검사하기
        $('input[name=name]').focusout(function(){

            let name = $(this).val();

            if(name.match(reName)){
                isNameOk = true;
                $('.nameResult').text('');
            }else{
                isNameOk = false;
                $('.nameResult').css('color', 'red').text('유효한 이름이 아닙니다.');
            }
        });
        /* --- 이름 유효성 검사 --- */

        // 이메일 검사하기
        $('input[name=email]').focusout(function(){

            let email = $(this).val();

            if(email.match(reEmail)){
                isEmailOk = true;
                $('.emailResult').text('');
            }else{
                isEmailOk = false;
                $('.emailResult').css('color', 'red').text('유효하지 않는 이메일 입니다.');
            }
        });

//        // 이메일 인증 검사
//        let emailCode = 0;
//
//        $('#btnEmailAuth').click(function(){
//
//            let email = $('input[name=email]').val();
//
//            $.ajax({
//                url: '/Jboard2/user/emailAuth.do',
//                method: 'get',
//                data: {"email":email},
//                dataType: 'json',
//                success: function(data){
//                    //console.log(data);
//                    if(data.status == 1){
//                        // 메일 발송 성공
//                        emailCode = data.code;
//
//                        $('.emailResult').text('인증코드를 전송 했습니다. 이메일을 확인 하세요.');
//                        $('.auth').show();
//                    }else{
//                        // 메일 발송 실패
//                        $('.emailResult').text('이메일을 실패했습니다. 이메일을 확인 후 다시 하시기 바랍니다.');
//
//                    }
//
//
//                }
//            });
//        });
//
//        // 이메일 인증코드 확인
//        $('#btnEmailConfirm').click(function(){
//
//            let code = $('input[name=auth]').val();
//
//            if(code == emailCode){
//                isEmailAuthOk = true;
//                $('.emailResult').text('이메일이 인증 되었습니다.');
//            }
//        });

        // 휴대폰 검사하기
        $('input[name=hp]').focusout(function(){

            let hp = $(this).val();

            if(hp.match(reHp)){
                isHpOk = true;
                $('.hpResult').text('');
            }else{
                isHpOk = false;
                $('.msgHp').hide();
                $('.hpResult').css('color', 'red').text('유효하지 않는 휴대폰 입니다.');
            }
        });


        // 최종 폼 전송할 때
        $('.register > form').submit(function(){
            // 아이디 검증
            if(!isUidOk){alert('아이디를 확인 하십시요.'); return false;}
            // 비밀번호 검증
            if(!isPassOk){alert('비밀번호가 유효하지 않습니다.'); return false;}
            // 이름 검증
            if(!isNameOk){alert('이름이 유효하지 않습니다.'); return false;}
            // 이메일 검증
            if(!isEmailOk){alert('이메일이 유효하지 않습니다.'); return false;}
            // 휴대폰 검증
            if(!isHpOk){alert('휴대폰이 유효하지 않습니다.'); return false;}

            return true;
        });
    });
