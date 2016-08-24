/**
 * Utilities Site Scripts using Boostrap and JQuery js framworks
 * @author Fabrizio Torelli
 * @version 1.0
 */
/**
 * Form Validator
 * @return And object with the features of clear the form (clearForm)
 */
var defaultTypeDropdownValue = 'Select a Store type';
var defaultTypeDropdownFactoryValue = 'Factory';
var defaultTypeDropdownRetailValue = 'Retail';
var defaultTypeDropdownSelector = '<span class="caret typeCaret"></span>';
var defaultTypeDropdownFilledClass = 'filled';
var Validator = (function(){
	return {
		clearStoreForm: clearDataEntryForm,
		submitStoreForm: submitDataEntryForm,
		clearErrors: clearFieldErrors,
		runStoreFormSubmit: runStoreFormSubmit
	};
	function clearDataEntryForm(event) {
		$('#storeName').val('');
		$('#storeAddress').val('');
		$('#storeType').val('');
		$('#storeImage').val('');
		$('#storeZipCode').val('');
		$('#typeDropdownHeader').removeClass(defaultTypeDropdownFilledClass);
		$('#typeDropdownHeader').html(defaultTypeDropdownValue + defaultTypeDropdownSelector);
		event.preventDefault();
		return false;
	} 
	function clearFieldErrors(event, name) {
		$('#error-store-'+name).addClass('hidden');
	}
	function uploadFileViaServlet(name, file) {
		var postData = new FormData();
		postData.append("file", file);
		postData.append("newName", name);
		var url = '/uploadServlet';

        var xhr = new XMLHttpRequest();
        xhr.addEventListener("load", function (xhr){
        	try {
    			var data = JSON.parse(xhr.currentTarget.responseText);
    			if (data.successStatus) {
                  alert('Form Submitted!');
    			}
    			else {
    	              alert('error in ajax form submission : ' + data.messageText);
    			}
        	} catch(e) {
	              alert('error in ajax form submission : ' + e);
        	}
			//alert('Form Submitted!');
        }, false);
        xhr.open("POST", url, true); // If async=false, then you'll miss progress bar support.
        xhr.send(postData);
	}
	function submitDataEntryForm(event, form) {
		var valid = true;
		var formEncoded = {};
		formEncoded["storeName"] = $('#storeName').val();
		formEncoded["storeAddress"] = $('#storeAddress').val();
		formEncoded["storeType"] = $('#storeType').val();
		formEncoded["storeImage"] = $('#storeImage').val();
		formEncoded["storeZipCode"] = $('#storeZipCode').val();
		if(formEncoded.storeName === '') {
			valid=false;
			$('#error-store-name').removeClass('hidden');
		}
		if(formEncoded.storeAddress=== '') {
			valid=false;
			$('#error-store-address').removeClass('hidden');
		}
		if(formEncoded.storeType=== '') {
			valid=false;
			$('#error-store-type').removeClass('hidden');
		}
		if(formEncoded.storeImage=== '' || !$('#storeImage')[0].files || !$('#storeImage')[0].files[0]) {
			valid=false;
			$('#error-store-image').removeClass('hidden');
		}
		else {
			formEncoded['storeImageType'] = $('#storeImage')[0].files[0].type;
		}
		var zipCodeRegex = new RegExp();
		zipCodeRegex.compile(/[a-zA-Z0-9 -]{3,20}$/g);
		var zipCodeValid=zipCodeRegex.test(formEncoded.storeZipCode);
		if(formEncoded.storeZipCode=== '' || !zipCodeValid) {
			valid=false;
			$('#error-store-zipcode').removeClass('hidden');
		}
		if (!!valid) {
			var file = $('#storeImage')[0].files && $('#storeImage')[0].files[0] ? $('#storeImage')[0].files[0] : null;
			var url = '/jax-rs/cms/insert';
			var getData = $(form).serialize();
			$.ajax({
				url: url,
				type: 'POST',
				data: encodeURIComponent(JSON.stringify(formEncoded)),
				async: false,
				cache: false,
				contentType: "application/x-www-form-urlencoded",
				dataType: "application/json",
//				processData: false,
				success: function (data) {
					console.log(data);
					if (!!data && !!file) {
						uploadFileViaServlet(data.messageText, file);
					}
				},
				error: function( jqXHR, textStatus, errorThrown){
					if (jqXHR.status === 200) {
						var data = JSON.parse(jqXHR.responseText);
						console.log(data);
						if (!!data && !!file) {
							uploadFileViaServlet(data.messageText, file);
						}
					}
					else {
						alert("error in ajax form submission");
					}
				}
			});
		}
		event.preventDefault();
		return false;
	} 
	function runStoreFormSubmit(event) {
		if ($( "#storeForm" )[0]) {
			$( "#storeForm" )[0].submit();
		}
	}
})();
var Events = (function(){
	return {
		switchToFactory: dropdownSwitchToFactory,
		switchToRetail: dropdownSwitchToRetail
	};
	function dropdownSwitchToFactory(event) {
		$('#storeType').val('F');
		$('#typeDropdownHeader').addClass(defaultTypeDropdownFilledClass);
		$('#typeDropdownHeader').html(defaultTypeDropdownFactoryValue + defaultTypeDropdownSelector);
		if (!$('#error-store-type').hasClass('hidden')) {
			$('#error-store-type').addClass('hidden');
		}
		event.preventDefault();
		return false;
	} 
	function dropdownSwitchToRetail(event) {
		$('#storeType').val('R');
		$('#typeDropdownHeader').addClass(defaultTypeDropdownFilledClass);
		$('#typeDropdownHeader').html(defaultTypeDropdownRetailValue + defaultTypeDropdownSelector);
		if (!$('#error-store-type').hasClass('hidden')) {
			$('#error-store-type').addClass('hidden');
		}
		event.preventDefault();
		return false;
	} 
})();

//$('.typeAddon').on('click', function(event) {
//	Validator.clearErrors(event, 'type');
//});