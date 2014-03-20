function check(f, v) {
	var fr = document.forms[f];
	var errs = "";
	for (n in v) {
		if (! fr[n].value.match(v[n])) {
			
			errs = errs + fr[n].name +  ': ' + fr[n].value + "\n";
		} ;
	}
	if (errs) {
		alert("Bad values:\n" + errs);
		return false;

	}
	fr.submit();
	return true;
}
