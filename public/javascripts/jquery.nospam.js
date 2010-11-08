/* 
 * No Spam (1.3)
 * by Mike Branski (www.leftrightdesigns.com)
 * mikebranski@gmail.com
 *
 * Copyright (c) 2008 Mike Branski (www.leftrightdesigns.com)
 * Licensed under GPL (www.leftrightdesigns.com/library/jquery/nospam/gpl.txt)
 *
 * NOTE: This script requires jQuery to work.  Download jQuery at www.jquery.com
 *
 * Thanks to Bill on the jQuery mailing list for the double slash idea!
 *
 * CHANGELOG:
 * v 1.3   - Added support for e-mail addresses with multiple dots (.) both before and after the at (@) sign
 * v 1.2.1 - Included GPL license
 * v 1.2   - Finalized name as No Spam (was Protect Email)
 * v 1.1   - Changed switch() to if() statement
 * v 1.0   - Initial release
 *
 */

jQuery.fn.nospam = function(settings) {
	settings = jQuery.extend({
		replaceText: false, 	// optional, accepts true or false
		filterLevel: 'normal' 	// optional, accepts 'low' or 'normal'
	}, settings);
	
	return this.each(function(){
		e = null;
		if(settings.filterLevel == 'low') { // Can be a switch() if more levels added
			if($(this).is('a[rel]')) {
				e = $(this).attr('rel').replace('//', '@').replace(/\//g, '.');
			} else {
				e = $(this).text().replace('//', '@').replace(/\//g, '.');
			}
		} else { // 'normal'
			if($(this).is('a[rel]')) {
				e = $(this).attr('rel').split('').reverse().join('').replace('//', '@').replace(/\//g, '.');
			} else {
				e = $(this).text().split('').reverse().join('').replace('//', '@').replace(/\//g, '.');
			}
		}
		if(e) {
			if($(this).is('a[rel]')) {
				$(this).attr('href', 'mailto:' + e);
				if(settings.replaceText) {
					$(this).text(e);
				}
			} else {
				$(this).text(e);
			}
		}
	});
};
