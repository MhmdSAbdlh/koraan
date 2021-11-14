var btn = new Array();
var progress = document.getElementById("myBar");
init();

function init(){
	//Define the buttons
	let sowar = ["الفاتحة","البقرة","آل عمران","النساء","المائدة","الأنعام","الأعراف","الأنفال","التوبة","يونس",
				"هود","يوسف","الرعد","إبراهيم","الحجر","النحل","الإسراء","الكهف","مريم","طه",
				"الأنبياء","الحج","المؤمنون","النور","الفرقان","الشعراء","النمل","القصص","العنكبوت","الروم",
				"لقمان","السجدة","الأحزاب","سبأ","فاطر","يس","الصافات","ص","الزمر","غافر",
				"فصلت","الشورى","الزخرف","الدخان","الجاثية","الأحقاف","محمد","الفتح","الحجرات","ق",
				"الذاريات","الطور","النجم","القمر","الرحمن","الواقعة","الحديد ← الممتحنة","الصف ← الطلاق","التحريم ← المعارج","نوح ← الإنسان",
				"المرسلات ← البروج","الطارق ← الناس"];
	for(let i=0;i<sowar.length;i++){
		btn[i] = document.createElement("button");
		var t = document.createTextNode(sowar[i]);
		btn[i].className = "btns";
		btn[i].appendChild(t);
		btn[i].onclick = function(){
			btn[i].className = "disabledBtn";
			btn[i].disabled = true;
			window.localStorage.setItem(localStorage.length+1,btn[i].innerHTML);
			document.getElementById('leftS').innerText = "السور المتبقية: " +(62-localStorage.length); 
			document.getElementById('doneS').innerText = "السور المنجزة: "+localStorage.length;
			document.getElementById('todayS').innerText = "سورة اليوم: " +localStorage.getItem(localStorage.length);
			progress.style.width = localStorage.length * 100 / 62 + "%";
			endRead();
		}
		document.getElementById('bodyDiv').appendChild(btn[i]);
	}
	
	//Import from local storage
	for(let i=0; i<btn.length; i++){
		for(let k=1;k<=localStorage.length;k++){
			if(btn[i].innerHTML == window.localStorage.getItem(k)){
				btn[i].className = "disabledBtn";
				btn[i].disabled = true;
			}
		}
	}
	progress.style.width = localStorage.length * 100 / 62 + "%";
	document.getElementById('doneS').innerText = "السور المنجزة: "+localStorage.length;
	document.getElementById('leftS').innerText = "السور المتبقية: " +(62-localStorage.length);
	endRead();
	if(localStorage.length==0){
		document.getElementById('todayS').innerText = "سورة اليوم: غير محدد";
	}
	else{
		document.getElementById('todayS').innerText = "سورة اليوم: " +localStorage.getItem(localStorage.length);
	}
}

function randomS(){
	if(localStorage.length<62){
		let index = Math.floor(Math.random()*62);
		while(btn[index].disabled == true){
			index = Math.floor(Math.random()*62);
		}
		btn[index].className = "disabledBtn";
		btn[index].disabled = true;
		window.localStorage.setItem(localStorage.length+1,btn[index].innerHTML);
		document.getElementById('leftS').innerText = "السور المتبقية: " +(62-localStorage.length); 
		document.getElementById('doneS').innerText = "السور المنجزة: "+localStorage.length;
		document.getElementById('todayS').innerText = "سورة اليوم: " +localStorage.getItem(localStorage.length);
		progress.style.width = localStorage.length * 100 / 62 + "%";
	}
	endRead();
}

function clearS(){
	for(let i=0; i<btn.length; i++){
		btn[i].className = "btns";
		btn[i].disabled = false;
	}
	localStorage.clear();
	document.getElementById('doneS').innerText = "السور المنجزة: "+localStorage.length;
	document.getElementById('leftS').innerText = "السور المتبقية: " +(62-localStorage.length); 
	document.getElementById('todayS').innerText = "سورة اليوم: غير محدد";
	progress.style.width = localStorage.length * 100 / 62 + "%";
}

function goBack(){
	if(localStorage.length>0){
		for(let i=0; i<btn.length; i++){
			if(btn[i].innerHTML == localStorage.getItem(localStorage.length)){
				btn[i].className = "btns";
				btn[i].disabled = false;
				break;
			}
		}
		localStorage.removeItem(localStorage.length);
		document.getElementById('doneS').innerText = "السور المنجزة: "+localStorage.length;
		document.getElementById('leftS').innerText = "السور المتبقية: " +(62-localStorage.length); 
		document.getElementById('todayS').innerText = "سورة اليوم: " +localStorage.getItem(localStorage.length);
		progress.style.width = localStorage.length * 100 / 62 + "%";
		if(localStorage.length==0){
			document.getElementById('todayS').innerText = "سورة اليوم: غير محدد";
		}
		else{
			document.getElementById('todayS').innerText = "سورة اليوم: " +localStorage.getItem(localStorage.length);
		}
	}
}

function endRead(){
	if(localStorage.length == 62){
		Swal.fire({
			title: 'أحسنت!',
			text: "لقد ختمت القرآن الكريم",
			icon: 'success',
			showCancelButton: true,
			confirmButtonColor: '#346751',
			cancelButtonColor: '#161616',
			confirmButtonText: 'دعاء ختم القرآن',
			cancelButtonText: `هل ترغب بالبدء مرة أخرة؟`,
		  }).then((result) => {
			if (result.isConfirmed){
				Swal.fire({
					html: true,
					title: 'دعاء ختم القرآن!',
					icon: 'warning',
					html: "*اللَّهُمَّ ارْحَمْنِي بالقُرْءَانِ وَاجْعَلهُ لِي إِمَاماً وَنُوراً وَهُدًى وَرَحْمَةً<br />*اللَّهُمَّ ذَكِّرْنِي مِنْهُ مَانَسِيتُ وَعَلِّمْنِي مِنْهُ مَاجَهِلْتُ وَارْزُقْنِي تِلاَوَتَهُ آنَاءَ اللَّيْلِ وَأَطْرَافَ النَّهَارِ وَاجْعَلْهُ لِي حُجَّةً يَارَبَّ العَالَمِينَ<br />*اللَّهُمَّ أَصْلِحْ لِي دِينِي الَّذِي هُوَ عِصْمَةُ أَمْرِي، وَأَصْلِحْ لِي دُنْيَايَ الَّتِي فِيهَا مَعَاشِي، وَأَصْلِحْ لِي آخِرَتِي الَّتِي فِيهَا مَعَادِي، وَاجْعَلِ الحَيَاةَ زِيَادَةً لِي فِي كُلِّ خَيْرٍ وَاجْعَلِ المَوْتَ رَاحَةً لِي مِنْ كُلِّ شَرٍّ<br />*اللَّهُمَّ اجْعَلْ خَيْرَ عُمْرِي آخِرَهُ وَخَيْرَ عَمَلِي خَوَاتِمَهُ وَخَيْرَ أَيَّامِي يَوْمَ أَلْقَاكَ فِيهِ<br />*اللَّهُمَّ إِنِّي أَسْأَلُكَ عِيشَةً هَنِيَّةً وَمِيتَةً سَوِيَّةً وَمَرَدًّا غَيْرَ مُخْزٍ وَلاَ فَاضِحٍ<br />*اللَّهُمَّ إِنِّي أَسْأَلُكَ خَيْرَ المَسْأَلةِ وَخَيْرَ الدُّعَاءِ وَخَيْرَ النَّجَاحِ وَخَيْرَ العِلْمِ وَخَيْرَ العَمَلِ وَخَيْرَ الثَّوَابِ وَخَيْرَ الحَيَاةِ وَخيْرَ<br />*المَمَاتِ وَثَبِّتْنِي وَثَقِّلْ مَوَازِينِي وَحَقِّقْ إِيمَانِي وَارْفَعْ دَرَجَتِي وَتَقَبَّلْ صَلاَتِي وَاغْفِرْ خَطِيئَاتِي وَأَسْأَلُكَ العُلَا مِنَ الجَنَّةِ<br />*اللَّهُمَّ إِنِّي أَسْأَلُكَ مُوجِبَاتِ رَحْمَتِكَ وَعَزَائِمِ مَغْفِرَتِكَ وَالسَّلاَمَةَ مِنْ كُلِّ إِثْمٍ وَالغَنِيمَةَ مِنْ كُلِّ بِرٍّ وَالفَوْزَ بِالجَنَّةِ وَالنَّجَاةَ مِنَ النَّارِ<br />*اللَّهُمَّ أَحْسِنْ عَاقِبَتَنَا فِي الأُمُورِ كُلِّهَا، وَأجِرْنَا مِنْ خِزْيِ الدُّنْيَا وَعَذَابِ الآخِرَةِ<br />*اللَّهُمَّ اقْسِمْ لَنَا مِنْ خَشْيَتِكَ مَاتَحُولُ بِهِ بَيْنَنَا وَبَيْنَ مَعْصِيَتِكَ وَمِنْ طَاعَتِكَ مَاتُبَلِّغُنَا بِهَا جَنَّتَكَ وَمِنَ اليَقِينِ مَاتُهَوِّنُ بِهِ عَلَيْنَا مَصَائِبَ الدُّنْيَا<br />*وَمَتِّعْنَا بِأَسْمَاعِنَا وَأَبْصَارِنَا وَقُوَّتِنَا مَاأَحْيَيْتَنَا وَاجْعَلْهُ الوَارِثَ مِنَّا وَاجْعَلْ ثَأْرَنَا عَلَى مَنْ ظَلَمَنَا وَانْصُرْنَا عَلَى مَنْ عَادَانَا وَلاَ تجْعَلْ مُصِيبَتَنَا فِي دِينِنَا وَلاَ تَجْعَلِ الدُّنْيَا أَكْبَرَ هَمِّنَا وَلَا مَبْلَغَ عِلْمِنَا وَلاَ تُسَلِّطْ عَلَيْنَا مَنْ لَا يَرْحَمُنَا<br />*اللَّهُمَّ لَا تَدَعْ لَنَا ذَنْبًا إِلَّا غَفَرْتَهُ وَلَا هَمَّا إِلَّا فَرَّجْتَهُ وَلَا دَيْنًا إِلَّا قَضَيْتَهُ وَلَا حَاجَةً مِنْ حَوَائِجِ الدُّنْيَا وَالآخِرَةِ إِلَّا قَضَيْتَهَا يَاأَرْحَمَ الرَّاحِمِينَ<br />*رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ وَصَلَّى اللهُ عَلَى سَيِّدِنَا وَنَبِيِّنَا مُحَمَّدٍ وَعَلَى آلِهِ وَأَصْحَابِهِ الأَخْيَارِ وَسَلَّمَ تَسْلِيمًا كَثِيراً.",
			});}
			else if(result.dismiss === Swal.DismissReason.cancel){
				Swal.fire(
				  'مسح!',
				  'تم المسح بنجاح.',
				  'info'
				)
				clearS();
			  }
		  })
	}
}