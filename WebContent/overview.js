var test_url = "test";
var signup_url = "signup";


function test(t, r) {
	var xhttp = new XMLHttpRequest();

	/*sur reception de la reponse*/
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			document.getElementById(r).innerHTML = this.responseText; }};

	/*texte a envoyer*/
	var params = "?text=" + document.getElementById(t).value;

	/*envoi asynchrone au servlet*/
	xhttp.open("GET", test_url + params , true);
	xhttp.send();
}

function signup(u, p, c, r) {
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
	xhttp.open("POST", signup_url , true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
}