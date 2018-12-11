$(function() {
	findTableInfo();
});

function findTableInfo() {
	$.post('mgr/special/findSpecialTable', function(data) {
		var tbody = $('tbody.tbody').empty();
		// if (!$.isSuccess(data)) return;
		var head = [ "西城区", "东城区", "海淀区", "通州区", "延庆区", "怀柔区", "朝阳区" ]
		var list = new Array();
		var sumrow;
		var sumcol = [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ];
		var sumsum = 0;
		for (var j = 0; j <= 6; j++) {
			sumrow = 0;
			for (var k = 0; k <= 12; k++) {
				list[k] = "";
			}
			$.each(data.body, function(i, e) {
				if (e.area == head[j]) {
					list[e.month - 1] = e.totalTime;
					sumrow += e.totalTime;
					sumcol[e.month - 1] += e.month;
				}
			});
			sumsum += sumrow;
			$('<tr></tr>').append($('<td></td>').append(head[j])).append(
					$('<td>' + list[0] + '</td>').append()).append(
					$('<td></td>').append(list[1])).append(
					$('<td></td>').append(list[2])).append(
					$('<td></td>').append(list[3])).append(
					$('<td></td>').append(list[4])).append(
					$('<td></td>').append(list[5])).append(
					$('<td></td>').append(list[6])).append(
					$('<td></td>').append(list[7])).append(
					$('<td></td>').append(list[8])).append(
					$('<td></td>').append(list[9])).append(
					$('<td></td>').append(list[10])).append(
					$('<td></td>').append(list[11])).append(
					$('<td></td>').append(sumrow)).appendTo(tbody);
		}
		$('<tr></tr>').append($('<td></td>').append("合计")).append(
				$('<td></td>').append(sumcol[0])).append(
				$('<td></td>').append(sumcol[1])).append(
				$('<td></td>').append(sumcol[2])).append(
				$('<td></td>').append(sumcol[3])).append(
				$('<td></td>').append(sumcol[4])).append(
				$('<td></td>').append(sumcol[5])).append(
				$('<td></td>').append(sumcol[6])).append(
				$('<td></td>').append(sumcol[7])).append(
				$('<td></td>').append(sumcol[8])).append(
				$('<td></td>').append(sumcol[9])).append(
				$('<td></td>').append(sumcol[10])).append(
				$('<td></td>').append(sumcol[11])).append(
				$('<td></td>').append(sumsum)).appendTo(tbody);
	}, 'json');
}
