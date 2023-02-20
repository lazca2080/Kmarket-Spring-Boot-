/*
    날짜 : 2023/02/17
    이름 : 조광호
    내용 : admin 상품 수정 및 삭제
*/
$(function(){
    // 상품삭제
    $('.remove').click(function(e){
        e.preventDefault();

        let delOk = confirm("정말 삭제하시겠습니까?")
        let url = "/Kmarket/admin/product/oneDelete"
        let chkNo = $("input[name='check']").val();
        let productNow = $(this);

        if(delOk){
            console.log(productNow); // 638

            $.ajax({
                url:url,
                method:'post',
                data:{chkNo : chkNo},
                dataType:'json',
                success: (data)=>{
                    if(data.result == 1){
                        alert([chkNo] + '번 상품이 성공적으로 삭제되었습니다.')
                        console.log(productNow)
                        productNow.parent().parent().remove();
                    }else{
                        alert('delete fail...')
                    }
                }
            })
        }
    });

    // 상품 수정
    $('.modify').click(function(e){
        e.preventDefault();

        let tr  = $(this).parent().parent();
        let td = tr.children();
        let txt = $(this).text();

         // 수정할 DOM 찾기
         let prodName = td.eq(3);
         let price = td.eq(4);
         let discount = td.eq(5);
         let point = td.eq(6);
         let stock = td.eq(7);

         // 내용 가져오기
         let content1 = prodName.text();
         let content2 = price.text();
         let content3 = discount.text()
         let content4 = point.text();
         let content5 = stock.text();

         if(txt == '[수정]'){
             $(this).text('[수정완료]').css('white-space','nowrap');

             prodName.html('<textarea style="resize:none; width:300px; height:80px;">' + content1 + '</textarea>');
             price.html('<textarea style="resize:none; width:100px; height:20px;">' + content2 + '</textarea>');
             discount.html('<textarea style="resize:none; width:50px; height:20px;">' + content3 + '</textarea>');
             point.html('<textarea style="resize:none; width:50px; height:20px;">' + content4 + '</textarea>');
             stock.html('<textarea style="resize:none; width:50px; height:20px;">' + content5 + '</textarea>');
             prodName.focus();
         }else{
            $(this).text('수정');

            let prodNo   = $(this).attr('data-no');
            let content1 = prodName.children(0).val();
            let content2 = price.children(0).val();
            let content3 = discount.children(0).val();
            let content4 = point.children(0).val();
            let content5 = stock.children(0).val();


            let jsonData = {
                "prodNo":prodNo,
                "prodName":content1,
                "price":content2,
                "discount":content3,
                "point":content4,
                "stock":content5
            };

            fetch("", {
                method:"post",
                headers:{
                    "Content-Type": "application/json",
                },
                body:jsonData,
            })
            .then((response)=> console.log("response: ", response))
            .catch((error) => console.log("eroor : ", error))
            
            console.log(1)
            
         }

    });
});









