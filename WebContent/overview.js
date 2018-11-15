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


function questions_get() {
	var r = "questions_get_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", "questions" , true);
	xhttp.send();
}

function sessions_delete() {
	var r = "sessions_delete_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};

	/*envoi asynchrone au servlet*/
	xhttp.open("DELETE", "sessions" , true);
	xhttp.send();
}

function sessions_get() {
	var r = "sessions_get_resp_id";
	var xhttp = new XMLHttpRequest();
	
	/*sur reception de la reponse*/
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", "sessions" , true);
	xhttp.send();
}

function test() {
	var t = "test_text_id";
	var r = "test_resp_id";
	var xhttp = new XMLHttpRequest();

	/*sur reception de la reponse*/
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};

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
	var r = "users_post_resp_id";
	var xhttp = new XMLHttpRequest();

	/*sur reception de la reponse*/
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};
	
	/*texte a envoyer*/
	var params = 
		"username=" + document.getElementById(u).value +
		"&password=" + document.getElementById(p).value +
		"&confirmation=" + document.getElementById(c).value;
			
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
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};
			
	/*texte a envoyer*/
	var params = 
		"username=" + document.getElementById(u).value +
		"&password=" + document.getElementById(p).value;
	
	/*envoi asynchrone au servlet*/
	xhttp.open("POST", "sessions" , true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}