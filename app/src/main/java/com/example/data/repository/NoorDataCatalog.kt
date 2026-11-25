package com.example.data.repository

import com.example.R
import com.example.data.model.CityInfo
import com.example.data.model.DailyQuote
import com.example.data.model.DuaItem
import com.example.data.model.FaqItem
import com.example.data.model.HadithItem
import com.example.data.model.IslamicEvent
import com.example.data.model.MasjidItem
import com.example.data.model.NaatItem
import com.example.data.model.WallpaperItem

object NoorDataCatalog {

    val cities: List<CityInfo> = listOf(
        CityInfo("Thakurdwara", "UP, India", 29.1939, 78.8678, "24°C", "Clear", "05:23", "06:45", "12:05", "15:30", "17:18", "18:45", "05:13", "17:18"),
        CityInfo("Moradabad", "UP, India", 28.8386, 78.7733, "25°C", "Sunny", "05:24", "06:46", "12:06", "15:31", "17:19", "18:46", "05:14", "17:19"),
        CityInfo("Rampur", "UP, India", 28.8030, 79.0250, "25°C", "Clear", "05:22", "06:44", "12:04", "15:29", "17:17", "18:44", "05:12", "17:17"),
        CityInfo("Bareilly", "UP, India", 28.3670, 79.4304, "24°C", "Pleasant", "05:20", "06:42", "12:02", "15:28", "17:16", "18:43", "05:10", "17:16"),
        CityInfo("Lucknow", "UP, India", 26.8467, 80.9462, "26°C", "Clear", "05:12", "06:33", "11:56", "15:24", "17:12", "18:38", "05:02", "17:12"),
        CityInfo("Delhi", "NCR, India", 28.6139, 77.2090, "24°C", "Breeze", "05:30", "06:52", "12:12", "15:37", "17:25", "18:52", "05:20", "17:25"),
        CityInfo("Mumbai", "Maharashtra, India", 19.0760, 72.8777, "29°C", "Humid", "05:38", "06:51", "12:26", "16:00", "18:01", "19:16", "05:28", "18:01"),
        CityInfo("Kolkata", "West Bengal, India", 22.5726, 88.3639, "27°C", "Clear", "04:42", "05:57", "11:26", "14:57", "16:54", "18:11", "04:32", "16:54"),
        CityInfo("Hyderabad", "Telangana, India", 17.3850, 78.4867, "28°C", "Clear", "05:14", "06:26", "12:04", "15:40", "17:41", "18:55", "05:04", "17:41"),
        CityInfo("Chennai", "Tamil Nadu, India", 13.0827, 80.2707, "30°C", "Warm", "05:01", "06:11", "11:54", "15:34", "17:38", "18:50", "04:51", "17:38"),
        CityInfo("Bangalore", "Karnataka, India", 12.9716, 77.5946, "25°C", "Cool", "05:12", "06:22", "12:05", "15:45", "17:49", "19:01", "05:02", "17:49"),
        CityInfo("Dubai", "UAE", 25.2048, 55.2708, "28°C", "Sunny", "05:21", "06:40", "12:09", "15:39", "17:34", "18:55", "05:11", "17:34"),
        CityInfo("Makkah", "Saudi Arabia", 21.4225, 39.8262, "31°C", "Clear", "05:18", "06:34", "12:12", "15:46", "17:48", "19:18", "05:08", "17:48"),
        CityInfo("Madina", "Saudi Arabia", 24.4672, 39.6111, "27°C", "Clear", "05:23", "06:42", "12:15", "15:45", "17:44", "19:14", "05:13", "17:44"),
        CityInfo("London", "United Kingdom", 51.5072, -0.1276, "11°C", "Cloudy", "05:54", "07:36", "11:53", "13:52", "16:02", "17:41", "05:44", "16:02")
    )

    val dailyQuotes: List<DailyQuote> = listOf(
        DailyQuote(
            "बेशक हर मुश्किल के साथ आसानी है। (सूरह अल-इंशिराह 94:6)",
            "بے شک ہر مشکل کے ساتھ آسانی ہے۔",
            "Indeed, with hardship comes ease. (Surah Ash-Sharh 94:6)",
            "Al-Quran 94:6"
        ),
        DailyQuote(
            "तुम में से बेहतरीन वह है जो क़ुरआन सीखे और सिखाए।",
            "تم میں سے بہترین وہ ہے جو قرآن سیکھے اور سکھائے۔",
            "The best among you are those who learn the Quran and teach it.",
            "Sahih Al-Bukhari"
        ),
        DailyQuote(
            "अल्लाह को याद रखने से ही दिलों को सुकून मिलता है।",
            "اللہ کے ذکر سے ہی دلوں کو اطمینان ملتا ہے۔",
            "Verily, in the remembrance of Allah do hearts find rest.",
            "Al-Quran 13:28"
        ),
        DailyQuote(
            "सदक़ा माल को कम नहीं करता, बल्कि उसमें बरकत डालता है।",
            "صدقہ مال کو کم نہیں کرتا بلکہ اس میں برکت ڈالتا ہے۔",
            "Charity does not decrease wealth; rather, Allah increases honor through it.",
            "Sahih Muslim"
        )
    )

    val duas: List<DuaItem> = listOf(
        DuaItem(1, "सोने की दुआ (Sone ki Dua)", "Rozmarra", "اللَّهُمَّ بِاسْمِكَ أَمُوتُ وَأَحْيَا", "Allahumma Bismika Amutu Wa Ahya", "ऐ अल्लाह! तेरे नाम के साथ मैं सोता (मरता) हूँ और तेरे ही नाम के साथ जागता (जीता) हूँ।", "اے اللہ! تیرے نام سے مرتا ہوں اور جیتا ہوں۔", "O Allah, in Your name I die and I live."),
        DuaItem(2, "उठने की दुआ (Uthne ki Dua)", "Rozmarra", "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ", "Alhamdulillahi Lazee Ahyana Baada Ma Amatana Wa Ilaihin Nushoor", "तमाम तारीफ़ उस अल्लाह के लिए है जिसने हमें मौत (नींद) के बाद ज़िन्दगी अता फ़रमाई और उसी की तरफ़ लौट कर जाना है।", "تمام تعریف اس اللہ کے لیے ہے جس نے ہمیں مرنے کے بعد زندہ کیا۔", "All praise is for Allah who gave us life after having taken it from us and unto Him is the resurrection."),
        DuaItem(3, "खाना शुरू करने की दुआ (Khana Shuru)", "Rozmarra", "بِسْمِ اللَّهِ وَعَلَى بَرَكَةِ اللَّهِ", "Bismillahi Wa Ala Barakatillah", "अल्लाह के नाम से और अल्लाह की बरकत के साथ शुरू करता हूँ।", "اللہ کے نام سے اور اللہ کی برکت کے ساتھ۔", "In the name of Allah and with the blessings of Allah."),
        DuaItem(4, "खाना ख़त्म करने की दुआ (Khana Khatam)", "Rozmarra", "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مُسْلِمِينَ", "Alhamdulillahi Lazee Atamana Wa Saqana Wa Jaalana Muslimeen", "सब तारीफ़ अल्लाह के लिए है जिसने हमें खिलाया, पिलाया और हमें मुसलमान बनाया।", "سب تعریف اللہ کے لیے ہے جس نے ہمیں کھلایا اور پلایا۔", "All praise is due to Allah who fed us, gave us drink, and made us Muslims."),
        DuaItem(5, "घर से निकलने की दुआ (Ghar se Nikalne ki)", "Safar", "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ", "Bismillahi Tawakkal Tu Alallah Wa La Hawla Wa La Quwwata Illa Billah", "अल्लाह के नाम से, मैंने अल्लाह पर भरोसा किया, नेकी करने और बुराई से बचने की ताक़त सिर्फ़ अल्लाह ही से है।", "اللہ کے نام سے، میں نے اللہ پر بھروسہ کیا۔", "In the name of Allah, I place my trust in Allah; there is no might nor power except by Allah."),
        DuaItem(6, "घर में आने की दुआ (Ghar me Aane ki)", "Rozmarra", "بِسْمِ اللَّهِ وَلَجْنَا وَبِسْمِ اللَّهِ خَرَجْنَا وَعَلَى رَبِّنَا تَوَكَّلْنَا", "Bismillahi Walajna Wa Bismillahi Kharajna Wa Ala Rabbina Tawakkalna", "अल्लाह के नाम से हम दाख़िल हुए, अल्लाह के नाम से निकले और अपने रब पर हमने भरोसा किया।", "اللہ کے نام سے ہم داخل ہوئے اور اللہ کے نام سے نکلے۔", "In the name of Allah we enter, in the name of Allah we leave, and upon our Lord we depend."),
        DuaItem(7, "सफ़र की दुआ (Safar ki Dua)", "Safar", "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَٰذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ", "Subhanallazi Sakhkhara Lana Haza Wa Ma Kunna Lahu Muqrineen", "पाक है वह ज़ात जिसने इस सवारी को हमारे क़ाबू में कर दिया वरना हम इसे क़ाबू करने वाले न थे।", "پاک ہے وہ ذات جس نے اسے ہمارے تابع کر دیا۔", "Glory be to Him who has subjected this to us, and we could never have it [by our efforts]."),
        DuaItem(8, "मस्जिद जाने की दुआ (Masjid Jaane ki)", "Ibadat", "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ", "Allahummaftah Li Abwaba Rahmatik", "ऐ अल्लाह! मेरे लिए अपनी रहमत के दरवाज़े खोल दे।", "اے اللہ! میرے لیے اپنی رحمت کے دروازے کھول دے۔", "O Allah, open the gates of Your mercy for me."),
        DuaItem(9, "मस्जिद से निकलने की दुआ (Masjid se Nikalne ki)", "Ibadat", "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ", "Allahumma Inni Asaluka Min Fazlik", "ऐ अल्लाह! बेशक मैं तुझसे तेरा फ़ज़्ल (रोज़ी और भलाई) मांगता हूँ।", "اے اللہ! میں تجھ سے تیرا فضل مانگتا ہوں۔", "O Allah, indeed I ask You of Your bounty."),
        DuaItem(10, "वज़ू के बाद की दुआ (Wazu ke Baad)", "Ibadat", "أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ", "Ashhadu Alla Ilaha Illallahu Wahdahu La Sharika Lahu Wa Ashhadu Anna Muhammadan Abduhu Wa Rasuluh", "मैं गवाही देता हूँ कि अल्लाह के सिवा कोई माबूद नहीं, वह अकेला है, और मुहम्मद (ﷺ) उसके बन्दे और रसूल हैं।", "میں گواہی دیتا ہوں کہ اللہ کے سوا کوئی معبود نہیں۔", "I bear witness that there is no deity worthy of worship except Allah alone, and Muhammad is His servant and messenger."),
        DuaItem(11, "परेशानी की दुआ (Pareshani ki Dua)", "Rozmarra", "لَا إِلَٰهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ", "La Ilaha Illa Anta Subhanaka Inni Kuntu Minaz Zalimeen", "तेरे सिवा कोई माबूद नहीं, तू पाक है, बेशक मैं ही ग़लती करने वालों में से था।", "تیرے سوا کوئی معبود نہیں، تو پاک ہے، بے شک میں ظالموں میں سے تھا۔", "There is no deity except You; exalted are You. Indeed, I have been of the wrongdoers."),
        DuaItem(12, "बीमारी से शिफ़ा की दुआ (Bimari ki Dua)", "Rozmarra", "أَذْهِبِ الْبَأْسَ رَبَّ النَّاسِ اشْفِ أَنْتَ الشَّافِي", "Azhibal Baasa Rabban Naas Ishfi Antash Shafi", "ऐ लोगों के रब! तकलीफ़ को दूर कर दे और शिफ़ा अता फ़रमा, तू ही शिफ़ा देने वाला है।", "اے لوگوں کے رب! تکلیف دور کر اور شفا عطا فرما۔", "Remove the harm, O Lord of mankind, and heal, for You are the Healer."),
        DuaItem(13, "इम्तिहान व इल्म की दुआ (Exam ki Dua)", "Rozmarra", "رَبِّ زِدْنِي عِلْمًا", "Rabbi Zidni Ilma", "ऐ मेरे रब! मेरे इल्म में इज़ाफ़ा फ़रमा।", "اے میرے رب! میرے علم میں اضافہ فرما۔", "My Lord, increase me in knowledge."),
        DuaItem(14, "माँ-बाप के लिए दुआ (Maa Baap ke liye)", "Rozmarra", "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا", "Rabbir Hamhuma Kama Rabbayani Sagheera", "ऐ मेरे रब! उन दोनों (माँ-बाप) पर रहम फ़रमा जैसा उन्होंने बचपन में मेरी परवरिश की।", "اے میرے رب! ان دونوں پر رحم فرما جیسا انہوں نے بچپن میں میری پرورش کی۔", "My Lord, have mercy upon them as they brought me up when I was small."),
        DuaItem(15, "रिज़्क़ की दुआ (Rizq ki Dua)", "Rozmarra", "اللَّهُمَّ إِنِّي أَسْأَلُكَ رِزْقًا طَيِّبًا وَعِلْمًا نَافِعًا وَعَمَلًا مُتَقَبَّلًا", "Allahumma Inni Asaluka Rizqan Tayyiban Wa Ilman Nafian Wa Amalan Mutaqabbalan", "ऐ अल्लाह! मैं तुझसे पाक रिज़्क़, फ़ायदेमंद इल्म और क़ुबूल होने वाले अमल का सवाल करता हूँ।", "اے اللہ! میں تجھ سے پاکیزہ رزق اور نفع بخش علم مانگتا ہوں۔", "O Allah, I ask You for pure sustenance, beneficial knowledge, and accepted deeds."),
        DuaItem(16, "सुबह की दुआ (Subah ki Dua)", "Subah", "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ", "Asbahna Wa Asbahal Mulku Lillah Walhamdulillah", "हमने सुबह की और सारी बादशाहत अल्लाह के लिए है और सब तारीफ़ अल्लाह ही के लिए है।", "ہم نے صبح کی اور ساری بادشاہت اللہ کے لیے ہے۔", "We have reached the morning and at this very time all sovereignty belongs to Allah."),
        DuaItem(17, "शाम की दुआ (Sham ki Dua)", "Sham", "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ", "Amsaina Wa Amsal Mulku Lillah Walhamdulillah", "हमने शाम की और सारी बादशाहत अल्लाह के लिए है और तमाम तारीफ़ अल्लाह के लिए है।", "ہم نے شام کی اور ساری بادشاہت اللہ کے لیے ہے۔", "We have reached the evening and at this very time all sovereignty belongs to Allah."),
        DuaItem(18, "जुमा की दुआ (Jummah ki Dua)", "Jummah", "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ", "Allahumma Salli Ala Muhammadin Wa Ala Aali Muhammadin Kama Sallaita Ala Ibrahim", "ऐ अल्लाह! हज़रत मुहम्मद (ﷺ) और उनकी आल पर रहमत नाज़िल फ़रमा। (जुमा में दुरूद शरीफ़ की कसरत करें)", "اے اللہ! محمد ﷺ پر اور ان کی آل پر رحمت نازل فرما۔", "O Allah, send peace and blessings upon Muhammad and the family of Muhammad.")
    )

    val hadiths: List<HadithItem> = listOf(
        HadithItem(1, "خَيْرُكُمْ مَنْ تَعَلَّمَ الْقُرْآنَ وَعَلَّمَهُ", "सबसे बेहतरीन तुम में से वह है जो क़ुरआन सीखे और सिखाए।", "تم میں سے بہترین وہ ہے جو قرآن سیکھے اور سکھائے۔", "The best among you is the one who learns the Quran and teaches it.", "Sahih Bukhari"),
        HadithItem(2, "الطُّهُورُ شَطْرُ الْإِيمَانِ", "सफ़ाई आधा ईमान है।", "صفائی نصف ایمان ہے۔", "Cleanliness is half of faith.", "Sahih Muslim"),
        HadithItem(3, "إِنَّ مِنْ خِيَارِكُمْ أَحْسَنَكُمْ أَخْلَاقًا", "तुम में से बेहतरीन वह है जिसका अख़लाक़ सबसे अच्छा है।", "تم میں سے بہترین وہ ہے جس کے اخلاق سب سے اچھے ہیں۔", "The best among you are those who have the best manners and character.", "Sahih Bukhari"),
        HadithItem(4, "الْجَنَّةُ تَحْتَ أَقْدَامِ الْأُمَّهَاتِ", "माँ के क़दमों के नीचे जन्नत है।", "ماں کے قدموں تلے جنت ہے۔", "Paradise lies beneath the feet of your mother.", "Sunan An-Nasai"),
        HadithItem(5, "مَنْ لَا يَرْحَمُ لَا يُرْحَمُ", "जो रहम नहीं करता उस पर रहम नहीं किया जाता।", "جو رحم نہیں کرتا اس پر رحم نہیں کیا جاتا۔", "He who does not show mercy will not be shown mercy.", "Sahih Bukhari"),
        HadithItem(6, "الدُّعَاءُ مُخُّ الْعِبَادَةِ", "दुआ इबादत का मग़ज़ (जड़) है।", "دعا عبادت کا مغز ہے۔", "Supplication (Dua) is the essence of worship.", "Jami At-Tirmidhi"),
        HadithItem(7, "وَالصَّبْرُ ضِيَاءٌ", "सब्र रौशनी है।", "صبر روشنی ہے۔", "Patience is illumination.", "Sahih Muslim"),
        HadithItem(8, "أَحَبُّ الْأَعْمَالِ إِلَى اللَّهِ أَدْوَمُهَا وَإِنْ قَلَّ", "अल्लाह को सबसे पसंदीदा अमल वह है जो हमेशा किया जाए चाहे थोड़ा हो।", "اللہ کو سب سے پسندیدہ عمل وہ ہے جو ہمیشہ کیا جائے خواہ تھوڑا ہو۔", "The most beloved deeds to Allah are those done consistently, even if small.", "Sahih Bukhari"),
        HadithItem(9, "مَنْ كَانَ يُؤْمِنُ بِاللَّهِ فَلْيُكْرِمْ جَارَهُ", "पड़ोसी का ख़याल रखो और उसके साथ अच्छा बर्ताव करो।", "اپنے پڑوسی کا خیال رکھو۔", "Whoever believes in Allah should be generous and kind to his neighbor.", "Sahih Bukhari"),
        HadithItem(10, "إِيَّاكُمْ وَالْكَذِبَ فَإِنَّ الْكَذِبَ يَهْدِي إِلَى الْفُجُورِ", "झूठ से बचो, झूठ गुनाह की तरफ़ ले जाता है।", "جھوٹ سے بچو، جھوٹ گناہ کی طرف لے جاتا ہے۔", "Beware of lying, for lying leads to wickedness.", "Sahih Muslim"),
        HadithItem(11, "أَكْمَلُ الْمُؤْمِنِينَ إِيمَانًا أَحْسَنُهُمْ خُلُقًا", "ईमान वालों में सबसे कामिल वह है जिसका अख़लाक़ बेहतरीन है।", "مومنوں میں کامل ترین ایمان والا وہ ہے جس کا اخلاق بہترین ہو۔", "The most complete believer in faith is the one with the best character.", "At-Tirmidhi"),
        HadithItem(12, "إِنَّ الصَّدَقَةَ لَتُطْفِئُ غَضَبَ الرَّبِّ", "ख़ैरात (सदक़ा) मुसीबत को टाल देती है।", "صدقہ مصیبت کو ٹال دیتا ہے۔", "Charity extinguishes calamities and the anger of the Lord.", "At-Tirmidhi"),
        HadithItem(13, "خَيْرُ الْكَسْبِ كَسْبُ يَدِ الرَّجُلِ", "मेहनत की कमाई सबसे बेहतरीन कमाई है।", "محنت کی کمائی سب سے بہترین کمائی ہے۔", "The best earning is that which a man earns with his own hands.", "Musnad Ahmad"),
        HadithItem(14, "إِنَّ اللَّهَ رَفِيقٌ يُحِبُّ الرِّفْقَ فِي الْأَمْرِ كُلِّهِ", "अल्लाह नर्म है और नर्मी को पसंद करता है।", "اللہ نرم ہے اور نرمی کو پسند کرتا ہے۔", "Allah is gentle and loves gentleness in all matters.", "Sahih Bukhari"),
        HadithItem(15, "مَنْ لَا يَشْكُرُ النَّاسَ لَا يَشْكُرُ اللَّهَ", "जो शख़्स लोगों का शुक्र नहीं करता वह अल्लाह का भी शुक्र नहीं करता।", "جو لوگوں کا شکر ادا نہیں کرتا وہ اللہ کا شکر بھی نہیں کرتا۔", "Whoever is not grateful to people is not grateful to Allah.", "Abu Dawud"),
        HadithItem(16, "طَلَبُ الْعِلْمِ فَرِيضَةٌ عَلَى كُلِّ مُسْلِمٍ", "इल्म हासिल करना हर मुसलमान पर फ़र्ज़ है।", "علم حاصل کرنا ہر مسلمان پر فرض ہے۔", "Seeking knowledge is an obligation upon every Muslim.", "Ibn Majah"),
        HadithItem(17, "خَيْرُ الْأَصْحَابِ مَنْ ذَكَّرَكَ بِاللَّهِ", "बेहतरीन दोस्त वह है जो अल्लाह की याद दिलाए।", "بہترین دوست وہ ہے جو اللہ کی یاد دلائے۔", "The best companion is the one who reminds you of Allah.", "Al-Tabarani"),
        HadithItem(18, "لَا تَغْضَبْ وَلَكَ الْجَنَّةُ", "ग़ुस्सा मत करो, जन्नत तुम्हारी है।", "غصہ مت کرو، جنت تمہاری ہے۔", "Do not get angry, and Paradise will be yours.", "At-Tabarani"),
        HadithItem(19, "لَا تَحْقِرَنَّ مِنَ الْمَعْرُوفِ شَيْئًا", "छोटा भी नेक काम हो तो उसे छोटा मत समझो।", "کسی بھی نیکی کو حقیر مت سمجھو۔", "Do not belittle any good deed, even meeting your brother with a cheerful face.", "Sahih Muslim"),
        HadithItem(20, "لَا يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لِأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ", "तुम में से कोई तब तक मोमिन नहीं जब तक अपने भाई के लिए वही पसंद न करे जो अपने लिए करता है।", "تم میں سے کوئی کامل مومن نہیں جب تک اپنے بھائی کے لیے وہی پسند نہ کرے جو اپنے لیے کرتا ہے۔", "None of you truly believes until he loves for his brother what he loves for himself.", "Sahih Bukhari")
    )

    val islamicEvents: List<IslamicEvent> = listOf(
        IslamicEvent("Ramzan 2027 Start (1st Roza)", "1 Ramzan 1448 AH", "18 Feb 2027", "महीना-ए-रमज़ान की मुबारक शुरुआत (चाँद दिखने के अनुसार)।", highlight = true),
        IslamicEvent("Shab-e-Qadr", "27 Ramzan 1448 AH", "16 Mar 2027", "हज़ार महीनों से बेहतर बरकत वाली रात।", highlight = true),
        IslamicEvent("Eid-ul-Fitr", "1 Shawwal 1448 AH", "20 Mar 2027", "मीठी ईद — रमज़ान के बाद खुशी और शुक्राने का दिन।", highlight = true),
        IslamicEvent("Yawm-e-Arafah", "9 Dhul-Hijjah 1448 AH", "26 May 2027", "हज का सबसे अज़ीम दिन और रोज़ा रखने की फ़ज़ीलत।"),
        IslamicEvent("Eid-ul-Azha", "10 Dhul-Hijjah 1448 AH", "27 May 2027", "बक़रीद — सुन्नत-ए-इब्राहीमी और क़ुर्बानी का दिन।", highlight = true),
        IslamicEvent("Islamic New Year (1 Muharram)", "1 Muharram 1449 AH", "16 Jun 2027", "हिजरी नया साल 1449 की शुरुआत।"),
        IslamicEvent("Ashura (10 Muharram)", "10 Muharram 1449 AH", "25 Jun 2027", "यौम-ए-आशूरा — रोज़ा व इबादत का दिन।", highlight = true),
        IslamicEvent("Eid Milad-un-Nabi (ﷺ)", "12 Rabi-ul-Awwal 1449 AH", "25 Aug 2027", "प्यारे नबी (ﷺ) की विलादत की याद।"),
        IslamicEvent("Shab-e-Meraj", "27 Rajab 1448 AH", "16 Jan 2027", "नबी-ए-करीम (ﷺ) का मुबारक सफ़र-ए-मेराज।"),
        IslamicEvent("Shab-e-Barat", "15 Shaban 1448 AH", "3 Feb 2027", "मग़फ़िरत और इबादत की मुबारक रात।", highlight = true)
    )

    val masjids: List<MasjidItem> = listOf(
        MasjidItem(1, "Jama Masjid Thakurdwara", "Thakurdwara", "Bazar Kalan, Thakurdwara", "0.4 km", "05:40 AM", "01:15 PM", "04:00 PM", "05:23 PM", "07:30 PM", "01:30 PM", 29.1939, 78.8678),
        MasjidItem(2, "Markazi Masjid Thakurdwara", "Thakurdwara", "Kashipur Road, Thakurdwara", "0.9 km", "05:45 AM", "01:30 PM", "04:05 PM", "05:23 PM", "07:45 PM", "01:45 PM", 29.1982, 78.8712),
        MasjidItem(3, "Eidgah Masjid Thakurdwara", "Thakurdwara", "Eidgah Road, Thakurdwara", "1.3 km", "05:35 AM", "01:15 PM", "03:55 PM", "05:23 PM", "07:15 PM", "01:25 PM", 29.1895, 78.8620),
        MasjidItem(4, "Noori Masjid Thakurdwara", "Thakurdwara", "Mohalla Ansariyan, Thakurdwara", "1.1 km", "05:45 AM", "01:20 PM", "04:00 PM", "05:23 PM", "07:30 PM", "01:30 PM", 29.1921, 78.8695),
        MasjidItem(5, "Madina Masjid Thakurdwara", "Thakurdwara", "Moradabad Road, Thakurdwara", "1.6 km", "05:40 AM", "01:30 PM", "04:05 PM", "05:23 PM", "07:40 PM", "01:40 PM", 29.1850, 78.8640),
        MasjidItem(6, "Bilal Masjid Thakurdwara", "Thakurdwara", "Surajnagar Road, Thakurdwara", "2.1 km", "05:45 AM", "01:15 PM", "04:00 PM", "05:23 PM", "07:30 PM", "01:30 PM", 29.2010, 78.8750),
        MasjidItem(7, "Jama Masjid Moradabad", "Moradabad", "Sadar Bazar, Moradabad", "1.2 km", "05:45 AM", "01:30 PM", "04:05 PM", "05:24 PM", "07:45 PM", "01:45 PM", 28.8386, 78.7733),
        MasjidItem(8, "Jama Masjid Delhi", "Delhi", "Chandni Chowk, Old Delhi", "2.0 km", "05:50 AM", "01:30 PM", "04:10 PM", "05:30 PM", "07:45 PM", "01:35 PM", 28.6507, 77.2334),
        MasjidItem(9, "Bara Imambara / Tile Wali Masjid", "Lucknow", "Chowk, Lucknow", "1.8 km", "05:30 AM", "01:15 PM", "03:55 PM", "05:17 PM", "07:30 PM", "01:30 PM", 26.8691, 80.9125)
    )

    val naatsAndBayans: List<NaatItem> = listOf(
        NaatItem(1, "Hasbi Rabbi Jallallah", "Noor Reciters", "Naat", 195, "हसबी रब्बी जल्लल्लाह, मा फ़ी क़ल्बी ग़ैरुल्लाह। नूर-ए-मुहम्मद सल्लल्लाह, ला इलाहा इल्लल्लाह।"),
        NaatItem(2, "Tajdar-e-Haram", "Traditional Qawwali", "Naat", 240, "किस्मत में मेरी चैन से जीना लिख दे, डूबे न कभी मेरा सफ़ीना लिख दे।"),
        NaatItem(3, "Mustafa Jaan-e-Rehmat Pe Lakhon Salam", "Salam-e-Raza", "Naat", 210, "मुस्तफ़ा जान-ए-रहमत पे लाखों सलाम, शम-ए-बज़्म-ए-हिदायत पे लाखों सलाम।"),
        NaatItem(4, "Ya Nabi Salam Alaika", "Maher Style", "Naat", 185, "या नबी सलाम अलैका, या रसूल सलाम अलैका, या हबीब सलाम अलैका।"),
        NaatItem(5, "Faslon Ko Takalluf Hai Humse Agar", "Noor Reciters", "Naat", 205, "फ़ासलों को तकल्लुफ़ है हमसे अगर, हम भी बेबस नहीं बेसहारा नहीं।"),
        NaatItem(6, "Karam Mangta Hoon", "Noor Reciters", "Naat", 220, "करम मांगता हूँ, अता मांगता हूँ, इलाही मैं तुझसे दुआ मांगता हूँ।"),
        NaatItem(7, "Subah Taiba Mein Hui", "Noor Reciters", "Naat", 175, "सुबह तैबा में हुई बटता है बाड़ा नूर का, सदक़ा लेने नूर का आया है तारा नूर का।"),
        NaatItem(8, "Maula Ya Salli Wa Sallim", "Noor Reciters", "Naat", 190, "मौला या सल्लि व सल्लिम दाइमन अबदन, अला हबीबिका ख़ैरिल ख़ल्क़ि कुल्लिहिमि।"),
        NaatItem(9, "Bayan: Namaz Ki Ahmiyat", "Thakurdwara Dars", "Bayan", 320, "नमाज़ मोमिन की मेराज है। दिन में पाँच वक़्त की नमाज़ दिल को सुकून और गुनाहों से पाकी देती है।"),
        NaatItem(10, "Bayan: Maa Baap Ki Khidmat", "Thakurdwara Dars", "Bayan", 290, "अल्लाह की रज़ा माँ-बाप की रज़ा में है। उनके साथ नर्म लहजे में बात करना सबसे बड़ा नेक अमल है।")
    )

    val wallpapers: List<WallpaperItem> = listOf(
        WallpaperItem(1, "Emerald Masjid Twilight", "Noor Signature Art", R.drawable.img_mosque_hero),
        WallpaperItem(2, "Golden Fanous Lantern", "Ramzan & Noor Art", R.drawable.img_lantern_art),
        WallpaperItem(3, "Sacred Holy Kaaba", "Makkah Medallion", R.drawable.img_kaaba_art),
        WallpaperItem(4, "Illuminated Al-Quran", "Emerald Rehal Art", R.drawable.img_quran_art),
        WallpaperItem(5, "Noor-us-Samawat", "Emerald & Gold", null, 0xFF0B3D2E, 0xFF051C14, 0xFFC9A227, "اللَّهُ نُورُ السَّمَاوَاتِ وَالْأَرْضِ"),
        WallpaperItem(6, "Bismillah Gold", "Royal Deep Green", null, 0xFF0A3327, 0xFF061E16, 0xFFE4BE4A, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"),
        WallpaperItem(7, "Ya Allah Ya Rahman", "Night Sanctuary", null, 0xFF082920, 0xFF03120D, 0xFFD4AF37, "يَا اللَّهُ يَا رَحْمَٰنُ يَا رَحِيمُ"),
        WallpaperItem(8, "La Ilaha Illallah", "Kalma Tayyab", null, 0xFF0D4433, 0xFF07251C, 0xFFC9A227, "لَا إِلَٰهَ إِلَّا اللَّهُ مُحَمَّدٌ رَسُولُ اللَّهِ"),
        WallpaperItem(9, "SubhanAllah Golden", "Dhikr Minimal", null, 0xFF103F30, 0xFF08221A, 0xFFF2D16B, "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ"),
        WallpaperItem(10, "Alhamdulillah", "Gratitude Gold", null, 0xFF0B3D2E, 0xFF165B46, 0xFFC9A227, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ"),
        WallpaperItem(11, "Ayat-ul-Kursi Shield", "Royal Mihrab", null, 0xFF072B20, 0xFF041610, 0xFFE6C153, "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ"),
        WallpaperItem(12, "Hasbunallah", "Trust in Allah", null, 0xFF0C4736, 0xFF052119, 0xFFC9A227, "حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ"),
        WallpaperItem(13, "Kun Fayakun", "Divine Decree", null, 0xFF083226, 0xFF03140F, 0xFFE0B843, "كُن فَيَكُونُ"),
        WallpaperItem(14, "Fabi-ayyi Ala-i", "Surah Ar-Rahman", null, 0xFF0E4938, 0xFF06251C, 0xFFC9A227, "فَبِأَيِّ آلَاءِ رَبِّكُمَا تُكَذِّبَانِ"),
        WallpaperItem(15, "Rabbi Zidni Ilma", "Seek Knowledge", null, 0xFF0B3D2E, 0xFF092A20, 0xFFE9C964, "رَبِّ زِدْنِي عِلْمًا"),
        WallpaperItem(16, "MaashaaAllah", "Blessing Star", null, 0xFF093629, 0xFF041912, 0xFFC9A227, "مَا شَاءَ اللَّهُ لَا قُوَّةَ إِلَّا بِاللَّهِ"),
        WallpaperItem(17, "Inna Ma'al Usri Yusra", "Hope & Ease", null, 0xFF0D4A38, 0xFF06241B, 0xFFE4BE4A, "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا"),
        WallpaperItem(18, "Durood Sharif", "Madina Glow", null, 0xFF073024, 0xFF031711, 0xFFC9A227, "صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ"),
        WallpaperItem(19, "Astaghfirullah", "Peaceful Night", null, 0xFF0A3A2B, 0xFF051C14, 0xFFDAB445, "أَسْتَغْفِرُ اللَّهَ الْعَظِيمَ"),
        WallpaperItem(20, "Thakurdwara Noor", "Islamic Crescent", null, 0xFF0B3D2E, 0xFF041A13, 0xFFC9A227, "نُورٌ عَلَىٰ نُورٍ")
    )

    val faqs: List<FaqItem> = listOf(
        FaqItem("1. Kya Noor App bina Internet ke (Offline) chalti hai?", "Haan! Quran, Duas, Tasbeeh, Hadith, 99 Names, Zakat Calculator, Islamic Calendar aur Kids Section 100% bina Internet ke chalte hain."),
        FaqItem("2. Mera Shehar ya GPS kaise badle?", "Upar green header me 📍 Shehar ke naam par tap karein aur list me se apna shehar chunein ya 'Auto GPS' dabayein. GPS band hone par default Thakurdwara, UP dikhta hai."),
        FaqItem("3. Thakurdwara ke Namaz ke waqt kis method par hain?", "University of Islamic Sciences, Karachi (Hanafi) method par set hain — Fajr 5:23, Dhuhr 12:05, Asr 3:30, Maghrib 5:18, Isha 6:45."),
        FaqItem("4. Azan Alarm 10 minute pehle kaise chalega?", "Namaz page par 'Azan Alarm (10 min pehle)' switch on karein aur Notification permission allow karein."),
        FaqItem("5. Kya is app me Login ya Password dalna zaroori hai?", "Bilkul nahi! Koi Email, Password ya OTP nahi hai. Aapka data sirf aapke phone me surakshit rehta hai."),
        FaqItem("6. Qibla Compass agar hil na raha ho to kya karein?", "Phone ko figure-8 me ghumayein taaki magnetic sensor calibrate ho jaye. Agar emulator par hain to manual slider se bhi direction check kar sakte hain."),
        FaqItem("7. Zakat kab Farz hoti hai?", "Jab aapke paas 7.5 Tola Sona ya 52.5 Tola Chandi (ya uski qeemat ke barabar Cash/Bank) 1 saal tak rahe, to kul maal ka 2.5% Zakat Farz hoti hai."),
        FaqItem("8. Kya main apne phone ke Home Screen par Noor ka icon laga sakta hoon?", "Haan! Support page par 'Add to Home Screen' button dabakar shortcut pin karein."),
        FaqItem("9. Kya main Quran aur Dua ko Hindi, Urdu aur English me padh sakta hoon?", "Haan! More -> Language setting se aap Hindi, Urdu ya English chunn sakte hain."),
        FaqItem("10. App ko support ya donate kaise karein?", "Support page me 'Donate via UPI' section me ₹10, ₹50 ya ₹100 chunein ya UPI ID irfanaddf-1@okaxis par bhejein.")
    )
}
