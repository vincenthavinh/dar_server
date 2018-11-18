function display(id) {
	var divs = document.getElementsByTagName("div");
	for(var i=0; i<divs.length; i++){
		if(divs[i].id==id){
			divs[i].hidden = false;
		}else{
			divs[i].hidden = true;
		}
	}
}

function users_get() {
	var m = "users_get_myself_id";
	var u = "users_get_username_id";
	var r = "users_get_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);
	
	/*texte a envoyer*/
	var params = "?username=" + document.getElementById(u).value
				
	if(document.getElementById(m).checked){
		params += "&myself=true";
	}

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", "users" + params , true);
	xhttp.send();
}

function answers_post() {
	var a = "answers_post_answer_id";
	var r = "answers_post_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);

	/*texte a envoyer*/
	var params = "answer=" + document.getElementById(a).value;
	
	/*envoi asynchrone au servlet*/
	xhttp.open("POST", "answers" , true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}

function questions_get() {
	var r = "questions_get_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", "questions" , true);
	xhttp.send();
}

function sessions_delete() {
	var r = "sessions_delete_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);

	/*envoi asynchrone au servlet*/
	xhttp.open("DELETE", "sessions" , true);
	xhttp.send();
}

function sessions_get() {
	var r = "sessions_get_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", "sessions" , true);
	xhttp.send();
}

function test() {
	var t = "test_text_id";
	var r = "test_resp_id";
	var xhttp = new XMLHttpRequest();

	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);

	/*texte a envoyer*/
	var params = "?text=" + document.getElementById(t).value;

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", "test" + params , true);
	xhttp.send();
}

function users_post() {
	var u = "users_post_username_id";
	var p = "users_post_password_id";
	var c = "users_post_confirmation_id";
	var f = "users_post_firstname_id";
	var n = "users_post_name_id";
	var e = "users_post_email_id";
	var r = "users_post_resp_id";
	var xhttp = new XMLHttpRequest();

	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);
	
	/*texte a envoyer*/
	var params = 
		"username=" + document.getElementById(u).value +
		"&password=" + document.getElementById(p).value +
		"&confirmation=" + document.getElementById(c).value +
		"&firstname=" + document.getElementById(f).value +
		"&name=" + document.getElementById(n).value +
		"&email=" + document.getElementById(e).value;
			
	/*envoi asynchrone au servlet*/
	xhttp.open("POST", "users" , true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}

function sessions_post(){
	var u = "sessions_post_username_id";
	var p = "sessions_post_password_id";
	var r = "sessions_post_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = createCallback(xhttp, r);
			
	/*texte a envoyer*/
	var params = 
		"username=" + document.getElementById(u).value +
		"&password=" + document.getElementById(p).value;
	
	/*envoi asynchrone au servlet*/
	xhttp.open("POST", "sessions" , true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}

function createCallback(xhttp, r){
	return function() {
		if (this.readyState == 4) {
			var textarea = document.getElementById(r);
			textarea.style.height = 'auto';
			textarea.value = xhttp.responseText;
			textarea.style.width = '100%';
			textarea.style.height = textarea.scrollHeight + "px";
		}
	}
}