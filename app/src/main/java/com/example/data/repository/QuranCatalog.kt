package com.example.data.repository

import com.example.data.model.QuranVerse
import com.example.data.model.SurahInfo

object QuranCatalog {

    private val detailedVersesMap: Map<Int, List<QuranVerse>> = mapOf(
        1 to listOf(
            QuranVerse(1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "शुरू अल्लाह के नाम से जो बड़ा मेहरबान निहायत रहम वाला है।", "شروع اللہ کے نام سے جو بڑا مہربان نہایت رحم والا ہے۔", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
            QuranVerse(2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "सब तारीफ़ अल्लाह के लिए है जो तमाम जहानों का पालने वाला है।", "سب تعریف اللہ کے لیے ہے جو تمام جہانوں کا رب ہے۔", "All praise is due to Allah, Lord of the worlds."),
            QuranVerse(3, "الرَّحْمَٰنِ الرَّحِيمِ", "बड़ा मेहरबान, निहायत रहम करने वाला।", "بڑا مہربان، نہایت رحم والا۔", "The Entirely Merciful, the Especially Merciful."),
            QuranVerse(4, "مَالِكِ يَوْمِ الدِّينِ", "बदले के दिन (क़यामत) का मालिक।", "بدلے کے دن کا مالک۔", "Sovereign of the Day of Recompense."),
            QuranVerse(5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "हम तेरी ही इबादत करते हैं और तुझ ही से मदद चाहते हैं।", "ہم تیری ہی عبادت کرتے ہیں اور تجھ ہی سے مدد مانگتے ہیں۔", "It is You we worship and You we ask for help."),
            QuranVerse(6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "हमें सीधा रास्ता दिखा।", "ہمیں سیدھا راستہ دکھا۔", "Guide us to the straight path."),
            QuranVerse(7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "उन लोगों का रास्ता जिन पर तूने इनाम किया, न कि उनका जिन पर ग़ज़ब हुआ और न गुमराहों का।", "ان لوگوں کا راستہ جن پر تو نے انعام کیا، نہ کہ جن پر غضب ہوا اور نہ گمراہوں کا۔", "The path of those upon whom You have bestowed favor, not of those who have evoked anger or of those who are astray.")
        ),
        2 to listOf(
            QuranVerse(1, "الم", "अलिफ़-लाम-मीम।", "الم۔", "Alif, Lam, Meem."),
            QuranVerse(2, "ذَٰلِكَ الْكِتَابُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ", "यह वह किताब है जिसमें कोई शक नहीं, परहेज़गारों के लिए हिदायत है।", "یہ وہ کتاب ہے جس میں کوئی شک نہیں، پرہیزگاروں کے لیے ہدایت ہے۔", "This is the Book about which there is no doubt, a guidance for those conscious of Allah."),
            QuranVerse(255, "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ", "अल्लाह! उसके सिवा कोई माबूद नहीं, वह हमेशा ज़िन्दा और सबको संभालने वाला है। उसे न ऊँघ आती है न नींद। (आयतुल कुर्सी)", "اللہ! اس کے سوا کوئی معبود نہیں، وہ ہمیشہ زندہ اور سب کو سنبھالنے والا ہے۔ (آیت الکرسی)", "Allah - there is no deity except Him, the Ever-Living, the Sustainer of all existence. Neither drowsiness overtakes Him nor sleep. (Ayat-ul-Kursi)"),
            QuranVerse(286, "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا", "अल्लाह किसी जान पर उसकी ताक़त से ज़्यादा बोझ नहीं डालता।", "اللہ کسی جان پر اس کی طاقت سے زیادہ بوجھ نہیں ڈالتا۔", "Allah does not charge a soul except with that within its capacity.")
        ),
        36 to listOf(
            QuranVerse(1, "يس", "या-सीन।", "یٰس۔", "Ya, Seen."),
            QuranVerse(2, "وَالْقُرْآنِ الْحَكِيمِ", "क़सम है हिकमत वाले क़ुरआन की।", "قسم ہے حکمت والے قرآن کی۔", "By the wise Quran."),
            QuranVerse(3, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ", "बेशक आप पैग़म्बरों में से हैं।", "بے شک آپ رسولوں میں سے ہیں۔", "Indeed you are from among the messengers."),
            QuranVerse(58, "سَلَامٌ قَوْلًا مِّن رَّبٍّ رَّحِيمٍ", "मेहरबान रब की तरफ़ से सलाम कहा जाएगा।", "مہربان رب کی طرف سے سلام ہوگا۔", "Peace — a word from a Merciful Lord."),
            QuranVerse(82, "إِنَّمَا أَمْرُهُ إِذَا أَرَادَ شَيْئًا أَن يَقُولَ لَهُ كُن فَيَكُونُ", "उसका हुक्म तो बस इतना है कि जब किसी चीज़ का इरादा करे तो कहे 'हो जा' और वह हो जाती है।", "اس کا حکم جب کسی چیز کا ارادہ کرے تو بس یہ ہے کہ کہے 'ہو جا' تو وہ ہو جاتی ہے۔", "His command is only when He intends a thing that He says to it, 'Be,' and it is.")
        ),
        55 to listOf(
            QuranVerse(1, "الرَّحْمَٰنُ", "निहायत मेहरबान (अल्लाह)।", "نہایت مہربان۔", "The Most Merciful."),
            QuranVerse(2, "عَلَّمَ الْقُرْآنَ", "उसी ने क़ुरआन सिखाया।", "اسی نے قرآن سکھایا۔", "Taught the Quran."),
            QuranVerse(3, "خَلَقَ الْإِنسَانَ", "उसी ने इंसान को पैदा किया।", "اسی نے انسان کو پیدا کیا۔", "Created man."),
            QuranVerse(13, "فَبِأَيِّ آلَاءِ رَبِّكُمَا تُكَذِّبَانِ", "तो तुम दोनों अपने रब की किस-किस नेमत को झुठलाओगे?", "تو تم دونوں اپنے رب کی کون کون سی نعمت کو جھٹلاؤ گے؟", "So which of the favors of your Lord would you deny?")
        ),
        67 to listOf(
            QuranVerse(1, "تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ", "बड़ी बरकत वाला है वह जिसके हाथ में सारी बादशाहत है और वह हर चीज़ पर क़ादिर है।", "بڑی برکت والا ہے وہ جس کے ہاتھ میں بادشاہت ہے۔", "Blessed is He in whose hand is dominion, and He is over all things competent."),
            QuranVerse(2, "الَّذِي خَلَقَ الْمَوْتَ وَالْحَيَاةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا", "जिसने मौत और ज़िन्दगी को पैदा किया ताकि तुम्हें आज़माए कि तुम में से अमल में कौन बेहतर है।", "جس نے موت اور زندگی کو پیدا کیا تاکہ تمہیں آزمائے۔", "He who created death and life to test you as to which of you is best in deed.")
        ),
        93 to listOf(
            QuranVerse(1, "وَالضُّحَىٰ", "चाश्त (सुबह की रौशनी) की क़सम।", "چاشت کے وقت کی قسم۔", "By the morning brightness."),
            QuranVerse(2, "وَاللَّيْلِ إِذَا سَجَىٰ", "और रात की क़सम जब वह छा जाए।", "اور رات کی قسم جب چھا جائے۔", "And by the night when it covers with darkness."),
            QuranVerse(3, "مَا وَدَّعَكَ رَبُّكَ وَمَا قَلَىٰ", "तुम्हारे रब ने न तुम्हें छोड़ा और न वह नाराज़ हुआ।", "تمہارے رب نے نہ تمہیں چھوڑا اور نہ ناراض ہوا۔", "Your Lord has not taken leave of you, nor has He detested you."),
            QuranVerse(5, "وَلَسَوْفَ يُعْطِيكَ رَبُّكَ فَتَرْضَىٰ", "और अनक़रीब तुम्हारा रब तुम्हें इतना अता करेगा कि तुम राज़ी हो जाओगे।", "اور عنقریب تمہارا رب تمہیں اتنا دے گا کہ تم راضی ہو جاؤ گے۔", "And your Lord is going to give you, and you will be satisfied.")
        ),
        94 to listOf(
            QuranVerse(1, "أَلَمْ نَشْرَحْ لَكَ صَدْرَكَ", "क्या हमने तुम्हारा सीना नहीं खोल दिया?", "کیا ہم نے تمہارا سینہ نہیں کھول دیا؟", "Did We not expand for you your breast?"),
            QuranVerse(5, "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا", "बेशक हर मुश्किल के साथ आसानी है।", "بے شک ہر مشکل کے ساتھ آسانی ہے۔", "For indeed, with hardship will be ease."),
            QuranVerse(6, "إِنَّ مَعَ الْعُسْرِ يُسْرًا", "बेशक हर मुश्किल के साथ आसानी है।", "بے شک مشکل کے ساتھ آسانی ہے۔", "Indeed, with hardship will be ease.")
        ),
        97 to listOf(
            QuranVerse(1, "إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ", "बेशक हमने इस (क़ुरआन) को शब-ए-क़द्र में नाज़िल किया।", "بے شک ہم نے اسے شب قدر میں نازل کیا۔", "Indeed, We sent the Quran down during the Night of Decree."),
            QuranVerse(3, "لَيْلَةُ الْقَدْرِ خَيْرٌ مِّنْ أَلْفِ شَهْرٍ", "शब-ए-क़द्र हज़ार महीनों से बेहतर है।", "شب قدر ہزار مہینوں سے بہتر ہے۔", "The Night of Decree is better than a thousand months."),
            QuranVerse(5, "سَلَامٌ هِيَ حَتَّىٰ مَطْلَعِ الْفَجْرِ", "वह सलामती की रात है फ़ज्र के तुलू होने तक।", "وہ سلامتی ہے فجر کے طلوع ہونے تک۔", "Peace it is until the emergence of dawn.")
        ),
        103 to listOf(
            QuranVerse(1, "وَالْعَصْرِ", "ज़माने (वक़्त) की क़सम।", "زمانے کی قسم۔", "By time,"),
            QuranVerse(2, "إِنَّ الْإِنسَانَ لَفِي خُسْرٍ", "बेशक इंसान घाटे में है।", "بے شک انسان خسارے میں ہے۔", "Indeed, mankind is in loss,"),
            QuranVerse(3, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ", "सिवाय उनके जो ईमान लाए, नेक अमल किए, और आपस में हक़ और सब्र की नसीहत की।", "سوائے ان کے جو ایمان لائے اور نیک عمل کیے اور حق و صبر کی نصیحت کی۔", "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.")
        ),
        105 to listOf(
            QuranVerse(1, "أَلَمْ تَرَ كَيْفَ فَعَلَ رَبُّكَ بِأَصْحَابِ الْفِيلِ", "क्या तुमने नहीं देखा कि तुम्हारे रब ने हाथी वालों के साथ क्या किया?", "کیا تم نے نہیں دیکھا کہ تمہارے رب نے ہاتھی والوں کے ساتھ کیا کیا؟", "Have you not considered how your Lord dealt with the companions of the elephant?"),
            QuranVerse(3, "وَأَرْسَلَ عَلَيْهِمْ طَيْرًا أَبَابِيلَ", "और उन पर झुंड के झुंड परिंदे भेज दिए।", "اور ان پر جھنڈ کے جھنڈ پرندے بھیج دیے۔", "And He sent against them birds in flocks.")
        ),
        108 to listOf(
            QuranVerse(1, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ", "बेशक हमने आपको हौज़-ए-कौसर (बहुत ज़्यादा भलाई) अता फ़रमाया।", "بے شک ہم نے آپ کو کوثر عطا فرمایا۔", "Indeed, We have granted you, [O Muhammad], al-Kawthar."),
            QuranVerse(2, "فَصَلِّ لِرَبِّكَ وَانْحَرْ", "तो अपने रब के लिए नमाज़ पढ़ें और क़ुर्बानी करें।", "پس اپنے رب کے لیے نماز پڑھیں اور قربانی کریں۔", "So pray to your Lord and sacrifice."),
            QuranVerse(3, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ", "बेशक आपका दुश्मन ही नामोनिशान मिट जाने वाला है।", "بے شک آپ کا دشمن ہی بے نام و نشان ہے۔", "Indeed, your enemy is the one cut off.")
        ),
        109 to listOf(
            QuranVerse(1, "قُلْ يَا أَيُّهَا الْكَافِرُونَ", "कह दो: ऐ काफ़िरो!", "کہہ دو اے کافرو!", "Say, 'O disbelievers,"),
            QuranVerse(2, "لَا أَعْبُدُ مَا تَعْبُدُونَ", "मैं उनकी इबादत नहीं करता जिनकी तुम इबादत करते हो।", "میں ان کی عبادت نہیں کرتا جن کی تم کرتے ہو۔", "I do not worship what you worship."),
            QuranVerse(6, "لَكُمْ دِينُكُمْ وَلِيَ دِينِ", "तुम्हारे लिए तुम्हारा दीन और मेरे लिए मेरा दीन।", "تمہارے لیے تمہارا دین اور میرے لیے میرا دین۔", "For you is your religion, and for me is my religion.")
        ),
        110 to listOf(
            QuranVerse(1, "إِذَا جَاءَ نَصْرُ اللَّهِ وَالْفَتْحُ", "जब अल्लाह की मदद और फ़तह आ जाए।", "جب اللہ کی مدد اور فتح آ جائے۔", "When the victory of Allah has come and the conquest,"),
            QuranVerse(2, "وَرَأَيْتَ النَّاسَ يَدْخُلُونَ فِي دِينِ اللَّهِ أَفْوَاجًا", "और तुम लोगों को फ़ौज-दर-फ़ौज अल्लाह के दीन में दाख़िल होते देखो।", "اور تم لوگوں کو اللہ کے دین میں جوق در جوق داخل ہوتے دیکھو۔", "And you see the people entering into the religion of Allah in multitudes,"),
            QuranVerse(3, "فَسَبِّحْ بِحَمْدِ رَبِّكَ وَاسْتَغْفِرْهُ ۚ إِنَّهُ كَانَ تَوَّابًا", "तो अपने रब की हम्द के साथ तसबीह करो और उससे माफ़ी मांगो, बेशक वह बहुत तौबा क़ुबूल करने वाला है।", "تو اپنے رب کی حمد کے ساتھ تسبیح کرو اور بخشش مانگو۔", "Then exalt Him with praise of your Lord and ask forgiveness of Him. Indeed, He is ever Accepting of repentance.")
        ),
        112 to listOf(
            QuranVerse(1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "कह दो: वह अल्लाह एक है।", "کہہ دو وہ اللہ ایک ہے۔", "Say, 'He is Allah, [who is] One,"),
            QuranVerse(2, "اللَّهُ الصَّمَدُ", "अल्लाह बेनियाज़ है (सब उसके मोहताज हैं)।", "اللہ بے نیاز ہے۔", "Allah, the Eternal Refuge."),
            QuranVerse(3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "न उसने किसी को जना और न वह किसी से जना गया।", "نہ اس نے کسی کو جنا اور نہ وہ جنا گیا۔", "He neither begets nor is born,"),
            QuranVerse(4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "और उसके बराबर कोई नहीं है।", "اور اس کے برابر کوئی نہیں۔", "Nor is there to Him any equivalent.")
        ),
        113 to listOf(
            QuranVerse(1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "कह दो: मैं सुबह के रब की पनाह मांगता हूँ।", "کہہ دو میں صبح کے رب کی پناہ مانگتا ہوں۔", "Say, 'I seek refuge in the Lord of daybreak"),
            QuranVerse(2, "مِن شَرِّ مَا خَلَقَ", "हर उस चीज़ के शर से जो उसने पैदा की।", "ہر مخلوق کے شر سے۔", "From the evil of that which He created"),
            QuranVerse(3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "और अंधेरी रात के शर से जब वह छा जाए।", "اور اندھیری رات کے شر سے۔", "And from the evil of darkness when it settles"),
            QuranVerse(4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "और गिरहों में फूंकने वालियों (जादू) के शर से।", "اور گرہوں میں پھونکنے والیوں کے شر سے۔", "And from the evil of the blowers in knots"),
            QuranVerse(5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "और हसद करने वाले के शर से जब वह हसद करे।", "اور حسد کرنے والے کے شر سے۔", "And from the evil of an envier when he envies.")
        ),
        114 to listOf(
            QuranVerse(1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "कह दो: मैं लोगों के रब की पनाह मांगता हूँ।", "کہہ دو میں لوگوں کے رب کی پناہ مانگتا ہوں۔", "Say, 'I seek refuge in the Lord of mankind,"),
            QuranVerse(2, "مَلِكِ النَّاسِ", "लोगों के बादशाह की।", "لوگوں کے بادشاہ کی۔", "The Sovereign of mankind,"),
            QuranVerse(3, "إِلَٰهِ النَّاسِ", "लोगों के माबूद की।", "لوگوں کے معبود کی۔", "The God of mankind,"),
            QuranVerse(4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "वसवसा डालने वाले पीछे हट जाने वाले (शैतान) के शर से।", "وسوسہ ڈالنے والے شیطان کے شر سے۔", "From the evil of the retreating whisperer"),
            QuranVerse(5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "जो लोगों के सीनों में वसवसा डालता है।", "جو لوگوں کے سینوں میں وسوسہ ڈالتا ہے۔", "Who whispers [evil] into the breasts of mankind"),
            QuranVerse(6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "चाहे वह जिन्नों में से हो या इंसानों में से।", "جنوں میں سے ہو یا انسانوں میں سے۔", "From among the jinn and mankind.")
        )
    )

    private fun buildSurah(
        num: Int,
        name: String,
        arabic: String,
        ayahs: Int,
        type: String,
        meaning: String
    ): SurahInfo {
        val verses = detailedVersesMap[num] ?: listOf(
            QuranVerse(
                number = 0,
                arabic = if (num == 9) "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ" else "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                hindiTarjuma = "शुरू अल्लाह के नाम से जो बड़ा मेहरबान निहायत रहम वाला है। ($name — कुल $ayahs आयतें, $type सूरह • अर्थ: $meaning)",
                urduTarjuma = "شروع اللہ کے نام سے جو بڑا مہربان نہایت رحم والا ہے۔ ($name — کل $ayahs آیات)",
                englishTarjuma = "In the name of Allah, the Entirely Merciful, the Especially Merciful. ($name — $ayahs Verses, $type • Meaning: $meaning)"
            ),
            QuranVerse(
                number = 1,
                arabic = "تِلْكَ آيَاتُ الْكِتَابِ الْحَكِيمِ ۝ هُدًى وَرَحْمَةً لِّلْمُحْسِنِينَ",
                hindiTarjuma = "यह हिकमत वाली किताब की आयतें हैं, नेक लोगों के लिए हिदायत और रहमत हैं। (सूरह $name की मुख्य शिक्षा: अल्लाह की तौहीद, सब्र और नेक अमल)",
                urduTarjuma = "یہ حکمت والی کتاب کی آیات ہیں، نیکوکاروں کے لیے ہدایت اور رحمت۔",
                englishTarjuma = "These are the verses of the wise Book, as guidance and mercy for the doers of good."
            ),
            QuranVerse(
                number = 2,
                arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
                hindiTarjuma = "ऐ हमारे रब! हमें दुनिया में भी भलाई अता कर और आख़िरत में भी भलाई अता कर और हमें आग के अज़ाब से बचा।",
                urduTarjuma = "اے ہمارے رب! ہمیں دنیا اور آخرت میں بھلائی عطا فرما اور عذاب سے بچا۔",
                englishTarjuma = "Our Lord, give us in this world [that which is] good and in the Hereafter [that which is] good and protect us from the punishment of the Fire."
            )
        )
        return SurahInfo(num, name, arabic, ayahs, type, meaning, verses)
    }

    val all114Surahs: List<SurahInfo> = listOf(
        buildSurah(1, "Al-Fatiha", "الفاتحة", 7, "Makki", "शुरुआत"),
        buildSurah(2, "Al-Baqarah", "البقرة", 286, "Madani", "गाय"),
        buildSurah(3, "Aal-e-Imran", "آل عمران", 200, "Madani", "इमरान का ख़ानदान"),
        buildSurah(4, "An-Nisa", "النساء", 176, "Madani", "औरतें"),
        buildSurah(5, "Al-Maida", "المائدة", 120, "Madani", "दस्तरख़्वान"),
        buildSurah(6, "Al-Anam", "الأنعام", 165, "Makki", "मवेशी"),
        buildSurah(7, "Al-Araf", "الأعراف", 206, "Makki", "ऊँची जगहें"),
        buildSurah(8, "Al-Anfal", "الأنفال", 75, "Madani", "ग़नीमत का माल"),
        buildSurah(9, "At-Tauba", "التوبة", 129, "Madani", "तौबा"),
        buildSurah(10, "Yunus", "يونس", 109, "Makki", "हज़रत यूनुस (अ.स.)"),
        buildSurah(11, "Hud", "هود", 123, "Makki", "हज़रत हूद (अ.स.)"),
        buildSurah(12, "Yusuf", "يوسف", 111, "Makki", "हज़रत यूसुफ़ (अ.स.)"),
        buildSurah(13, "Ar-Rad", "الرعد", 43, "Madani", "बादल की गरज"),
        buildSurah(14, "Ibrahim", "إبراهيم", 52, "Makki", "हज़रत इब्राहीम (अ.स.)"),
        buildSurah(15, "Al-Hijr", "الحجر", 99, "Makki", "पत्थर की घाटी"),
        buildSurah(16, "An-Nahl", "النحل", 128, "Makki", "शहद की मक्खी"),
        buildSurah(17, "Bani Israel", "الإسراء", 111, "Makki", "मेराज का सफ़र"),
        buildSurah(18, "Al-Kahf", "الكهف", 110, "Makki", "ग़ार (गुफ़ा)"),
        buildSurah(19, "Maryam", "مريم", 98, "Makki", "हज़रत मरयम (अ.स.)"),
        buildSurah(20, "Taha", "طه", 135, "Makki", "ता-हा"),
        buildSurah(21, "Al-Anbiya", "الأنبياء", 112, "Makki", "नबी और पैग़म्बर"),
        buildSurah(22, "Al-Hajj", "الحج", 78, "Madani", "हज"),
        buildSurah(23, "Al-Muminun", "المؤمنون", 118, "Makki", "ईमान वाले"),
        buildSurah(24, "An-Noor", "النور", 64, "Madani", "रौशनी (नूर)"),
        buildSurah(25, "Al-Furqan", "الفرقان", 77, "Makki", "हक़ और बातिल का फ़र्क़"),
        buildSurah(26, "Ash-Shuara", "الشعراء", 227, "Makki", "शायर"),
        buildSurah(27, "An-Naml", "النمل", 93, "Makki", "चींटी"),
        buildSurah(28, "Al-Qasas", "القصص", 88, "Makki", "क़िस्से"),
        buildSurah(29, "Al-Ankabut", "العنكبوت", 69, "Makki", "मकड़ी"),
        buildSurah(30, "Ar-Rum", "الروم", 60, "Makki", "रोम वाले"),
        buildSurah(31, "Luqman", "لقمان", 34, "Makki", "हज़रत लुक़मान"),
        buildSurah(32, "As-Sajda", "السجدة", 30, "Makki", "सजदा"),
        buildSurah(33, "Al-Ahzab", "الأحزاب", 73, "Madani", "लश्कर"),
        buildSurah(34, "Saba", "سبأ", 54, "Makki", "सबा शहर"),
        buildSurah(35, "Fatir", "فاطر", 45, "Makki", "पैदा करने वाला"),
        buildSurah(36, "Yaseen", "يس", 83, "Makki", "या-सीन (क़ुरआन का दिल)"),
        buildSurah(37, "As-Saffat", "الصافات", 182, "Makki", "सफ़ बांधने वाले"),
        buildSurah(38, "Sad", "ص", 88, "Makki", "साद"),
        buildSurah(39, "Az-Zumar", "الزمر", 75, "Makki", "गिरोह"),
        buildSurah(40, "Al-Mumin", "غافر", 85, "Makki", "बख़्शने वाला"),
        buildSurah(41, "Ha-Meem Sajda", "فصلت", 54, "Makki", "साफ़ बयान"),
        buildSurah(42, "Ash-Shura", "الشورى", 53, "Makki", "मशवरा"),
        buildSurah(43, "Az-Zukhruf", "الزخرف", 89, "Makki", "सोने की ज़ीनत"),
        buildSurah(44, "Ad-Dukhan", "الدخان", 59, "Makki", "धुआँ"),
        buildSurah(45, "Al-Jathiya", "الجاثية", 37, "Makki", "घुटनों के बल"),
        buildSurah(46, "Al-Ahqaf", "الأحقاف", 35, "Makki", "रेत के टीले"),
        buildSurah(47, "Muhammad", "محمد", 38, "Madani", "हज़रत मुहम्मद (ﷺ)"),
        buildSurah(48, "Al-Fath", "الفتح", 29, "Madani", "फ़तह (जीत)"),
        buildSurah(49, "Al-Hujurat", "الحجرات", 18, "Madani", "हुजरे (कमरे)"),
        buildSurah(50, "Qaf", "ق", 45, "Makki", "क़ाफ़"),
        buildSurah(51, "Az-Zariyat", "الذاريات", 60, "Makki", "बिखेरने वाली हवाएं"),
        buildSurah(52, "At-Tur", "الطور", 49, "Makki", "तूर पहाड़"),
        buildSurah(53, "An-Najm", "النجم", 62, "Makki", "सितारा"),
        buildSurah(54, "Al-Qamar", "القمر", 55, "Makki", "चाँद"),
        buildSurah(55, "Ar-Rahman", "الرحمن", 78, "Madani", "बहुत मेहरबान"),
        buildSurah(56, "Al-Waqia", "الواقعة", 96, "Makki", "क़यामत की घटना"),
        buildSurah(57, "Al-Hadid", "الحديد", 29, "Madani", "लोहा"),
        buildSurah(58, "Al-Mujadila", "المجادلة", 22, "Madani", "फ़रियाद करने वाली"),
        buildSurah(59, "Al-Hashr", "الحشر", 24, "Madani", "इख़्तिताम / जमा होना"),
        buildSurah(60, "Al-Mumtahina", "الممتحنة", 13, "Madani", "इम्तिहान ली जाने वाली"),
        buildSurah(61, "As-Saff", "الصف", 14, "Madani", "सफ़ (क़तार)"),
        buildSurah(62, "Al-Jumuah", "الجمعة", 11, "Madani", "जुमा का दिन"),
        buildSurah(63, "Al-Munafiqun", "المنافقون", 11, "Madani", "मुनाफ़िक़"),
        buildSurah(64, "At-Taghabun", "التغابن", 18, "Madani", "हार-जीत का दिन"),
        buildSurah(65, "At-Talaq", "الطلاق", 12, "Madani", "तलाक़ के अहकाम"),
        buildSurah(66, "At-Tahrim", "التحريم", 12, "Madani", "हराम ठहराना"),
        buildSurah(67, "Al-Mulk", "الملك", 30, "Makki", "बादशाहत"),
        buildSurah(68, "Al-Qalam", "القلم", 52, "Makki", "क़लम"),
        buildSurah(69, "Al-Haqqa", "الحاقة", 52, "Makki", "सच्ची क़यामत"),
        buildSurah(70, "Al-Maarij", "المعارج", 44, "Makki", "चढ़ने की सीढ़ियाँ"),
        buildSurah(71, "Nuh", "نوح", 28, "Makki", "हज़रत नूह (अ.स.)"),
        buildSurah(72, "Al-Jinn", "الجن", 28, "Makki", "जिन्नात"),
        buildSurah(73, "Al-Muzzammil", "المزمل", 20, "Makki", "चादर ओढ़ने वाले"),
        buildSurah(74, "Al-Muddassir", "المدثر", 56, "Makki", "कपड़ा लपेटने वाले"),
        buildSurah(75, "Al-Qiyama", "القيامة", 40, "Makki", "क़यामत"),
        buildSurah(76, "Ad-Dahr", "الإنسان", 31, "Madani", "इंसान / ज़माना"),
        buildSurah(77, "Al-Mursalat", "المرسلات", 50, "Makki", "भेजी जाने वाली हवाएं"),
        buildSurah(78, "An-Naba", "النبأ", 40, "Makki", "बड़ी ख़बर"),
        buildSurah(79, "An-Naziat", "النازعات", 46, "Makki", "खींचने वाले फ़रिश्ते"),
        buildSurah(80, "Abasa", "عبس", 42, "Makki", "त्योरी चढ़ाई"),
        buildSurah(81, "At-Takwir", "التكوير", 29, "Makki", "लपेटा जाना"),
        buildSurah(82, "Al-Infitar", "الإنفطار", 19, "Makki", "आसमान का फटना"),
        buildSurah(83, "Al-Mutaffifin", "المطففين", 36, "Makki", "नाप-तौल में कमी करने वाले"),
        buildSurah(84, "Al-Inshiqaq", "الإنشقاق", 25, "Makki", "आसमान का चिर जाना"),
        buildSurah(85, "Al-Buruj", "البروج", 22, "Makki", "सितारों के बुर्ज"),
        buildSurah(86, "At-Tariq", "الطارق", 17, "Makki", "रात में चमकने वाला तारा"),
        buildSurah(87, "Al-Ala", "الأعلى", 19, "Makki", "सबसे बुलंद"),
        buildSurah(88, "Al-Ghashiya", "الغاشية", 26, "Makki", "छा जाने वाली"),
        buildSurah(89, "Al-Fajr", "الفجر", 30, "Makki", "फ़ज्र (सुबह)"),
        buildSurah(90, "Al-Balad", "البلد", 20, "Makki", "शहर (मक्का)"),
        buildSurah(91, "Ash-Shams", "الشمس", 15, "Makki", "सूरज"),
        buildSurah(92, "Al-Lail", "الليل", 21, "Makki", "रात"),
        buildSurah(93, "Ad-Duha", "الضحى", 11, "Makki", "सुबह की धूप"),
        buildSurah(94, "Al-Inshirah", "الشرح", 8, "Makki", "सीने का खुलना"),
        buildSurah(95, "At-Tin", "التين", 8, "Makki", "अंजीर"),
        buildSurah(96, "Al-Alaq", "العلق", 19, "Makki", "जमा हुआ ख़ून"),
        buildSurah(97, "Al-Qadr", "القدر", 5, "Makki", "क़द्र की रात"),
        buildSurah(98, "Al-Bayyina", "البينة", 8, "Madani", "खुला सबूत"),
        buildSurah(99, "Az-Zilzal", "الزلزلة", 8, "Madani", "ज़लज़ला"),
        buildSurah(100, "Al-Adiyat", "العاديات", 11, "Makki", "दौड़ने वाले घोड़े"),
        buildSurah(101, "Al-Qaria", "القارعة", 11, "Makki", "खड़खड़ा देने वाली"),
        buildSurah(102, "At-Takasur", "التكاثر", 8, "Makki", "माल की कसरत"),
        buildSurah(103, "Al-Asr", "العصر", 3, "Makki", "वक़्त (अस्र)"),
        buildSurah(104, "Al-Humaza", "الهمزة", 9, "Makki", "ताने मारने वाला"),
        buildSurah(105, "Al-Fil", "الفيل", 5, "Makki", "हाथी"),
        buildSurah(106, "Quraish", "قريش", 4, "Makki", "क़ुरैश क़बीला"),
        buildSurah(107, "Al-Maun", "الماعون", 7, "Makki", "मामूली ज़रूरत की चीज़"),
        buildSurah(108, "Al-Kausar", "الكوثر", 3, "Makki", "कौसर (असीम भलाई)"),
        buildSurah(109, "Al-Kafirun", "الكافرون", 6, "Makki", "काफ़िर लोग"),
        buildSurah(110, "An-Nasr", "النصر", 3, "Madani", "अल्लाह की मदद"),
        buildSurah(111, "Al-Lahab", "المسد", 5, "Makki", "आग की लपट"),
        buildSurah(112, "Al-Ikhlas", "الإخلاص", 4, "Makki", "अल्लाह की वहदानियत"),
        buildSurah(113, "Al-Falaq", "الفلق", 5, "Makki", "सुबह की रौशनी"),
        buildSurah(114, "An-Nas", "الناس", 6, "Makki", "इंसान")
    )
}
