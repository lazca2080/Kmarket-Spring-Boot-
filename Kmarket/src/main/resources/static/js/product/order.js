// 전역 변수로 둔 이유는 포인트 변경 시 중복 차감되지 않게 하기 위해서
let count      = $('.final').children().children().children('tr:eq(0)').children('td:eq(1)').text().replace(' 건','');
let price      = $('.final').children().children().children('tr:eq(1)').children('td:eq(1)').text().replace(',','');
let discount   = $('.final').children().children().children('tr:eq(2)').children('td:eq(1)').text().replace(',','');
let delivery   = $('.final').children().children().children('tr:eq(3)').children('td:eq(1)').text().replace(',','');
let savePoint  = $('.final').children().children().children('tr:eq(4)').children('td:eq(1)').text().replace(',','');
let totalPrice = $('.final').children().children().children('tr:eq(6)').children('td:eq(1)').text().replace(',','');

$(function(){
	var applyPoint = document.getElementById('applyPoint');
	let userPoint  = Number(document.querySelector('span[class=userPoint]').innerHTML.replace(',',''));
	let finalPoint = $('.final').children().children().children('tr:eq(5)').children('td:eq(1)');
	var total      = $('.final').children().children().children('tr:eq(6)').children('td:eq(1)');
	var point = document.querySelector('input[name=point]');
	
	if(userPoint < 5000){
		$('input[name=point]').prop('readonly', true);
		$('input[name=point]').prop('value', 0);
	}
	
	applyPoint.addEventListener('click', function(){
		
		if(point.value > userPoint){
			alert('가지고 계신 포인트보다 많습니다.');
			$('input[name=point]').prop('value', 0);
		}else if(point.value < 5000){
			alert('5,000원 미만은 사용 할 수 없습니다.');
			$('input[name=point]').prop('value', 0);
		}else{
			finalPoint.text(Number(point.value).toLocaleString());
			// 최초 페이지 실행 시 저장되는 totalPrice에서 포인트 차감
			let num = (Number(totalPrice)-Number(point.value));
			total.text(Number(num).toLocaleString());
		}
		
	});
	
	$('.order > form ').submit(function(e){
		
		// 나머진 session에 있는 정보로 계산
		let orderer = $('input[name=orderer]').val();
		let hp = $('input[name=hp]').val();
		let zip = $('input[name=zip]').val();
		let addr1 = $('input[name=addr1]').val();
		let addr2 = $('input[name=addr2]').val();
		totalPrice = $('.final').children().children().children('tr:eq(6)').children('td:eq(1)').text().replace(',','');
		
		let jsonData = {
			"ordCount": count,
			"ordPrice": price,
			"ordDiscount": discount,
			"ordDelivery": delivery,
			"savePoint" : savePoint,
			"usedPoint": point.value,
			"ordTotPrice": totalPrice,
			"recipName": orderer,
			"recipHp": hp,
			"recipZip": zip,
			"recipAddr1": addr1,
			"recipAddr2": addr2,
			"ordPayment": $('input[name=payment]:checked').val()
		}
		
		
		$.ajax({
			url:'/Kmarket/product/complete',
			method:'POST',
			data: JSON.stringify(jsonData),
			contentType:'application/json',
			dataType:'JSON',
			success: function(data){
				if(data.result == 1){
					location.href = "/Kmarket/product/complete";
				}else{
					alert('다시 시도 해주세요');
				}
			}
		});
		
		e.preventDefault();
	});
});