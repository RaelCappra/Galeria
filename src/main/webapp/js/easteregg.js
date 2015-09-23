char_codes = [106, 115, 112];//jsp
do_easteregg = false;
function easteregg(){
		document.getElementById('easteregg').className = "visible";
		document.getElementById('audio').play();
		//play LOOMYNARTY sound
}
$(document).ready(function(){
	charlog = [];
	$('body').keypress(
		function(e){
			if(document.activeElement == document.body){
				
				if(charlog.length == char_codes.length){
					charlog = charlog.slice(1);
				}
				charlog.push(e.charCode);
				console.log(e.charCode);
				console.log(charlog);
				if(charlog.equals(char_codes) && do_easteregg){
					easteregg();
				}
			}
			
		});
});