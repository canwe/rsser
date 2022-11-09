$(document).ready(function() {
	$('#readAll').change(function() {
	    $.get((this.checked ? '/feed/readAll.json' : '/feed/unreadAll.json'), {
	    	'feedId' : $('#readAll').val()
	    });
	    
	    var allChecked = this.checked; 
	    
	    $(".entry").each(function() {
	    	this.checked = allChecked;
	    });
	});
	    
	$(".entry").change(function() {
	    $.get((this.checked ? '/feed/read.json' : '/feed/unread.json'), {
	    	'entryId' : $(this).val()
	    });
	});
	
	$("td.feedRow").click(function() {
		$(".read .entry", this).attr('checked', 'checked');
	    $.get('/feed/read.json', {
	    	'entryId' : $(".read .entry", this).val()
	    });
	});
});