// 전체 선택
function selectAll(selectAll) {
    // name=check인 10개의 체크박스 리턴됨.
	const checkboxes = document.getElementsByName('check');

	checkboxes.forEach((checkbox) => {
		checkbox.checked = selectAll.checked;
	})
}

// 상품삭제(선택삭제) Product-list
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

// 선택삭제 admin-CS
function csDelete1(){
    const url = "/Kmarket/admin/cs/deleteNotice"
    const replaceUrl = "/Kmarket/admin/cs/list/notice";
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
                    alert(valueArr.length + '개의 게시글이 정상적으로 삭제 되었습니다.')
                    location.href = replaceUrl;
                }else{
                    alert('삭제 실패')
                }
            }
        })
    }
}
function csDelete2(){
    const url = "/Kmarket/admin/cs/deleteFaq"
    const replaceUrl = "/Kmarket/admin/cs/list/faq";
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
                    alert(valueArr.length + '개의 게시글이 정상적으로 삭제 되었습니다.')
                    location.href = replaceUrl;
                }else{
                    alert('삭제 실패')
                }
            }
        })
    }
}
function csDelete3(){
    const url = "/Kmarket/admin/cs/deleteQna"
    const replaceUrl = "/Kmarket/admin/cs/list/qna";
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
                    alert(valueArr.length + '개의 게시글이 정상적으로 삭제 되었습니다.')
                    location.href = replaceUrl;
                }else{
                    alert('삭제 실패')
                }
            }
        })
    }
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
});

