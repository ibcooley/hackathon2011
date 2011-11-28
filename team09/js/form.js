function form(formid, posturl){
	$(formid).submit(function(event){
		event.preventDefault()
		$.ajax({
			type:     "POST",
			url:      posturl,
			data:     $(formid).serialize(),
			dataType: "json",
			success: function(msg){
				if(msg.error == 'false'){	
					alert(msg);				
					if(msg.href != '') window.location = msg.href;
					else {

					}
				}
				else if(msg.error == 'true') $('#error').html(msg.text);
			},
			error: function(msg){

			}
		});
	})
}