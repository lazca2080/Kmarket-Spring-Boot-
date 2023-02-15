let prodCount     = 0;
let totalCount    = 0;
let totalPrice    = 0;
let totalDiscount = 0;
let totalDelivery = 0;
let totalPoint    = 0;
let totalTotal    = 0;

// 중복 사용되는 스크립트는 function으로 묶어서 사용하기
function totalChange(prodCount, totalPrice, totalPoint, totalDelivery ,totalTotal){
	document.querySelector('td[class=count]').innerText = prodCount.toLocaleString();
	document.querySelector('td[class=price]').innerText = totalPrice.toLocaleString();
	document.querySelector('td[class=delivery]').innerText = totalDelivery.toLocaleString();
	document.querySelector('td[class=point]').innerText = totalPoint.toLocaleString();
	document.querySelector('td[class=total]').innerText = totalTotal.toLocaleString();
	document.querySelector('td[class=discount]').innerText = (((totalPrice + totalDelivery) - totalTotal)).toLocaleString();
}

// cartHtml 체크박스에 onclick으로 들어간 함수
function checkProd(prodNo){
	// prodNo에 맞는 클래스가 체크되어 있다면 <--- 이부분도 class와 id를 너무 남발하여 사용중이라 수정이 필요해보임
	if($('.'+prodNo).is(':checked')){
		prodCount     += 1;
		totalCount    += Number(document.getElementById(prodNo + 'count').innerText.replace(',', ''));
		totalPrice    += Number(document.getElementById(prodNo + 'price').innerText.replace(',', ''));
		totalDelivery += Number(document.getElementById(prodNo + 'delivery').innerText.replace(',', ''));
		totalPoint    += Number(document.getElementById(prodNo + 'point').innerText.replace(',', ''));
		totalTotal    += Number(document.getElementById(prodNo + 'total').innerText.replace(',', ''));
		
		// 상단 totalChange function
		totalChange(prodCount, totalPrice, totalPoint, totalDelivery, totalTotal);
		
	}else if(!$('.'+prodNo).is(':checked')) {
		prodCount     -= 1;
		totalCount    -= Number(document.getElementById(prodNo + 'count').innerText.replace(',', ''));
		totalPrice    -= Number(document.getElementById(prodNo + 'price').innerText.replace(',', ''));
		totalDelivery -= Number(document.getElementById(prodNo + 'delivery').innerText.replace(',', ''));
		totalPoint    -= Number(document.getElementById(prodNo + 'point').innerText.replace(',', ''));
		totalTotal    -= Number(document.getElementById(prodNo + 'total').innerText.replace(',', ''));
		
		// 상단 totalChange function
		totalChange(prodCount, totalPrice, totalPoint, totalDelivery, totalTotal);
	}
}
$(function(){
	// 선택 삭제 시 필요한 배열
	var checkList = [];
			
	// 전체 선택
	$('input[name=all]').click(function(){
		// 항시 초기화는 필수
		checkList= [];
		
		// 전체 선택 시 전부 체크
		$('input[name=prodNo]').prop('checked', true);
		
		// 전체 선택시 uid를 가지고 전체 계산을 하지만 checkList에 담는 이유는 선택 삭제시 필요하기 때문이다.
		$('input[name=prodNo]:checked').each(function(){ checkList.push($(this).val()); });
		
		// input hidden에 uid를 담지않고 controller에서 uid를 불러온다. 변조 방지
		$.ajax({
			url:'/Kmarket/product/cart/total',
			method:'POST',
			success: function(data){
				// 전체 선택 버튼이 체크되어 있다면
				if($('input[name=all]').is(':checked')){
					// attr로 true false 하게되면 상품 별 체크박스를 체크 or 해제 했을 때 다시 체크 안되는 경우가 발생하여 prop 사용
					$('input[name=prodNo]').prop('checked', true);
					
					// 이전에 이미 선택 되어 있는 상태에서 전체 선택 클릭하게되면 계산을 다시 해야하므로 0으로 초기화
					totalChange(0, 0, 0, 0, 0);
					
					// 전체 선택 상태에서 일부 상품 체크를 해제 or 체크했을 때 계산이 되어야하므로 상단 전역 변수 수정
					prodCount    = data.result.count
					totalPrice    = data.result.price
					totalDelivery = data.result.delivery
					totalPoint    = data.result.point
					totalTotal    = data.result.total
					
					// 상단 totalChange function
					totalChange(prodCount, totalPrice, totalPoint, totalDelivery, totalTotal);
				}else{
					$('input[name=prodNo]').prop('checked', false);
					totalChange(0, 0, 0, 0, 0);
					totalCount    = 0
					totalPrice    = 0
					totalDelivery = 0
					totalPoint    = 0
					totalTotal    = 0
					checkList=[];
				}
			}
		});
	});
	
	// 선택 삭제
	$('.del').click(function(){
		checkList = [];
		
		$('input[name=prodNo]:checked').each(function(){ checkList.push($(this).val()); });
		
		let jsonData = { 'checkList' : checkList };
		
		$.ajax({
			url:'/Kmarket/product/checkCart',
			method:'POST',
			data: jsonData,
			dataType:'JSON',
			success: function(data){
				if(checkList.length == data.result){
					alert('삭제 되었습니다.');
					location.href = "/Kmarket/product/cart";
				}else{
					alert('다시 시도해주세요.')
				}
			}
		});
	});
	
	// 주문하기
	$('.cart > form').submit(function(e){
		checkList = [];
		$('input[name=prodNo]:checked').each(function(){ checkList.push($(this).val()); });
		
		if(!$('input[name=prodNo]').is(':checked')){ alert('상품을 선택하세요'); }
		
		if(confirm('선택하신 상품으로 주문하시겠습니까?.')){
			
			$.ajax({
				url:'/Kmarket/product/order',
				method:'POST',
				data:{ 'checkList':checkList },
				dataType:'JSON',
				success: function(data){
					if(data.result == 1){
						location.href = "/Kmarket/product/order";
					}
				}
			});
		}
		e.preventDefault();
	});
})