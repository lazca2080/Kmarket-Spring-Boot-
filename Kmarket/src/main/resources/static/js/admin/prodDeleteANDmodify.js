/*
    날짜 : 2023/02/17
    이름 : 조광호
    내용 : admin 상품 수정 및 삭제
*/

// 전체 체크 클릭 시
function selectAll(selectAll) {
    // name=check인 10개의 체크박스 리턴됨.
	const checkboxes = document.getElementsByName('check');

	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}

$(function(){
	let checkObj = document.getElementsByName("check");
	let rowCount = checkObj.length;
	/**
     *  단일체크 처리
     *  모두 선택된 체크박스 중에서 단 하나라도 체크 해제되면 allCheck 박스만 해제되는 코드
     */
	$("input[name='check']").click(function(){
		if($("input[name='check']:checked").length == rowCount){
			$("input[name='allCheck']")[0].checked = true;
		} else {
			$("input[name='allCheck']")[0].checked = false;
		}
	});

    // 상품삭제
    $(document).on("click", ".remove", (e)=>{
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
                        productNow.parent().parent().hide();
                    }else{
                        alert('delete fail...')
                    }
                }
            })
        }
    });

    // 상품 수정
    $(document).on("click", ".modify", (e)=>{
        e.preventDefault();
    
        let tr = $(this).parent().css("color", "red");
        console.log(tr)
        // let td = tr.children();
        // let txt = $(this).text();

        // // 수정할 DOM 찾기
        // let prodName = td.eq(3);
        // let price = td.eq(4);
        // let discount = td.eq(5);
        // let point = td.eq(6);
        // let stock = td.eq(7);
        
        // // 내용 가져오기
        // let content1 = prodName.text();
        // console.log(content1);
        // let content2 = price.text();
        // console.log(content2);
        // let content3 = discount.text()
        // console.log(content3);
        // let content4 = point.text();
        // console.log(content4);
        // let content5 = stock.text();
        // console.log(content5);
    
        // if(txt == '[수정]'){
        //     $(this).text('[수정완료]');
        //     prodName.html('<textarea>'+content1+'</textarea>');
        // }
    
    })

});



// 상품삭제(선택삭제)
function checkDelete(){
    let url = "/Kmarket/admin/product/delete";
    let replaceUrl = "/Kmarket/admin/product/list";
    let valueArr = new Array();
    let deleteList = document.getElementsByName("check");

    for(let i = 0; i<deleteList.length; i++){
        if(deleteList[i].checked){
            valueArr.push(deleteList[i].value);
        }
    }

    if(valueArr == 0){
        alert('선택된 상품이 없습니다.')
    }else{
        confirm('정말 삭제하시겠습니까?')
        console.log(valueArr);
        $.ajax({
            url: url,
            method: 'post',
            traditional: true,
            data:{valueArr : valueArr},
            dataType:'json',
            success: (data)=>{
                if(data.result == 1){
                    alert(valueArr.length + '개의 상품이 정상적으로 삭제 되었습니다.')
                    location.replace(replaceUrl);
                }else{
                    alert('삭제 실패')
                }
            }
        })
    }
}


