let prodCount     = 0;
let totalCount    = 0;
let totalPrice    = 0;
let totalDiscount = 0;
let totalDelivery = 0;
let totalPoint    = 0;
let totalTotal    = 0;

function totalChange(prodCount, totalPrice, totalPoint, totalTotal){
	document.querySelector('td[class=count]').innerText = prodCount.toLocaleString();
	document.querySelector('td[class=price]').innerText = totalPrice.toLocaleString();
	document.querySelector('td[class=delivery]').innerText = totalDelivery.toLocaleString();
	document.querySelector('td[class=point]').innerText = totalPoint.toLocaleString();
	document.querySelector('td[class=total]').innerText = totalTotal.toLocaleString();
	document.querySelector('td[class=discount]').innerText = (((totalPrice + totalDelivery) - totalTotal)).toLocaleString();
}

function checkProd(prodNo){
	if($('.'+prodNo).is(':checked')){
		prodCount     += 1;
		totalCount    += Number(document.getElementById(prodNo + 'count').innerText);
		totalPrice    += Number(document.getElementById(prodNo + 'price').innerText);
		totalDelivery += Number(document.getElementById(prodNo + 'delivery').innerText);
		totalPoint    += Number(document.getElementById(prodNo + 'point').innerText);
		totalTotal    += Number(document.getElementById(prodNo + 'total').innerText);
		
		totalChange(prodCount, totalPrice, totalPoint, totalTotal);
		
	}else if(!$('.'+prodNo).is(':checked')) {
		prodCount     -= 1;
		totalCount    -= Number(document.getElementById(prodNo + 'count').innerText);
		totalPrice    -= Number(document.getElementById(prodNo + 'price').innerText);
		totalDelivery -= Number(document.getElementById(prodNo + 'delivery').innerText);
		totalPoint    -= Number(document.getElementById(prodNo + 'point').innerText);
		totalTotal    -= Number(document.getElementById(prodNo + 'total').innerText);
		
		totalChange(prodCount, totalPrice, totalPoint, totalTotal);
	}
}
$(function(){
	var checkList = [];
			
	// 전체 선택
	$('input[name=all]').click(function(){
		checkList= [];
		$('input[name=prodNo]').attr('checked', true);
		
		$('input[name=prodNo]:checked').each(function(){
			checkList.push($(this).val());
		});
		
		$.ajax({
			url:'/Kmarket/product/cart/total',
			method:'POST',
			success: function(data){
				
			}
		});
	});
	
	// 선택 삭제
	$('.del').click(function(){
		checkList = [];
		$('input[name=prodNo]:checked').each(function(){
			checkList.push($(this).val());
		});
		
		let jsonData = { 'checkList' : checkList };
		let prodNo = JSON.stringify(jsonData);
		
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