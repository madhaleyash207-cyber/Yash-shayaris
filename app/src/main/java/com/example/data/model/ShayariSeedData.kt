package com.example.data.model

object ShayariSeedData {
    val curatedShayaris: List<ShayariEntity> = listOf(
        // ==================== LOVE (प्रेम) - 6 Marathi Shayaris ====================
        ShayariEntity(
            text = "तुझ्या एका स्मितहास्यावर\nमाझं अख्खं जग थांबतं,\nशब्दांविना बोलणारं प्रेम\nफक्त तुझ्या डोळ्यांतच सापडतं.",
            author = "यश",
            category = ShayariCategory.LOVE.id,
            cardTheme = ShayariCardTheme.ROSE_LOVE.id,
            searchKeywords = "love prem smile hasya jag dole eyes",
            isDailyFeatured = true
        ),
        ShayariEntity(
            text = "हात तुझा हातात असावा,\nसोबत तुझाच प्रवास असावा,\nजन्मोजन्मीच्या या नात्याला\nफक्त तुझ्या प्रेमाचा सहवास असावा.",
            author = "यश",
            category = ShayariCategory.LOVE.id,
            cardTheme = ShayariCardTheme.ROSE_LOVE.id,
            searchKeywords = "love prem haat pravas nata sahavas"
        ),
        ShayariEntity(
            text = "पावसाच्या प्रत्येक थेंबात\nमला तुझीच आठवण येते,\nतू जवळ नसलीस तरीही\nतुझीच चाहूल मन वेचते.",
            author = "यश",
            category = ShayariCategory.LOVE.id,
            cardTheme = ShayariCardTheme.ROSE_LOVE.id,
            searchKeywords = "love prem paus rain aathvan chahul"
        ),
        ShayariEntity(
            text = "चांदण्या रात्रीपेक्षाही सुंदर\nतुझ्यासोबतची ती शांत वेळ असते,\nजिथे शब्दांची गरज नसते\nफक्त मनाशी मन बोलत असते.",
            author = "यश",
            category = ShayariCategory.LOVE.id,
            cardTheme = ShayariCardTheme.ROSE_LOVE.id,
            searchKeywords = "love prem chandanya ratra shant vel"
        ),
        ShayariEntity(
            text = "नातं हे फुलांसारखं असावं,\nकाट्यांतही सुगंध देणारं,\nआणि प्रेम हे तुझ्यासारखं असावं,\nअंधारातही प्रकाश देणारं.",
            author = "यश",
            category = ShayariCategory.LOVE.id,
            cardTheme = ShayariCardTheme.ROSE_LOVE.id,
            searchKeywords = "love prem phul sugandh prakash"
        ),
        ShayariEntity(
            text = "तुला पाहताच काळजात\nएक गोड स्पंदन उमटतं,\nकितीही लपवलं तरीही\nमाझं प्रेम डोळ्यांतून झळकतं.",
            author = "यश",
            category = ShayariCategory.LOVE.id,
            cardTheme = ShayariCardTheme.ROSE_LOVE.id,
            searchKeywords = "love prem kalaj spandan dole"
        ),

        // ==================== SAD (विरह) - 6 Marathi Shayaris ====================
        ShayariEntity(
            text = "हसणाऱ्या चेहऱ्यामागे कधीतरी\nओल्या पापण्या लपलेल्या असतात,\nकाही आठवणी शब्दांपेक्षा\nशांततेतच जास्त बोलत असतात.",
            author = "यश",
            category = ShayariCategory.SAD.id,
            cardTheme = ShayariCardTheme.INDIGO_SAD.id,
            searchKeywords = "sad virah dukh papnya aathvani shantata",
            isDailyFeatured = true
        ),
        ShayariEntity(
            text = "वेळ निघून जाते पण\nजखमा मात्र ताज्याच राहतात,\nज्यांना आपण आपलं मानतो\nतेच कधीतरी परके होऊन जातात.",
            author = "यश",
            category = ShayariCategory.SAD.id,
            cardTheme = ShayariCardTheme.INDIGO_SAD.id,
            searchKeywords = "sad dukh vel jakhma parke"
        ),
        ShayariEntity(
            text = "आजही तुझ्या वाटेकडे\nडोळे लागून राहिले आहेत,\nतुटलेल्या स्वप्नांचे तुकडे\nमनातच साठवून ठेवले आहेत.",
            author = "यश",
            category = ShayariCategory.SAD.id,
            cardTheme = ShayariCardTheme.INDIGO_SAD.id,
            searchKeywords = "sad virah vaat swapna dukh"
        ),
        ShayariEntity(
            text = "सगळ्यांशी हसून बोलतो पण\nआतून मन खूप एकटं वाटतं,\nज्याला सारं सांगायचं होतं\nत्याच्याचशी बोलणं राहून जातं.",
            author = "यश",
            category = ShayariCategory.SAD.id,
            cardTheme = ShayariCardTheme.INDIGO_SAD.id,
            searchKeywords = "sad ekta lonely man bolna"
        ),
        ShayariEntity(
            text = "नशिबाच्या पानांवर काही नाती\nअर्धवटच लिहिलेली असतात,\nजवळ असूनही काही माणसं\nमनापासून दूर गेलेली असतात.",
            author = "यश",
            category = ShayariCategory.SAD.id,
            cardTheme = ShayariCardTheme.INDIGO_SAD.id,
            searchKeywords = "sad nashib nati ardhavat dur"
        ),
        ShayariEntity(
            text = "अश्रू पुसायला कोणी नसतं तेव्हा\nरात्र खूप लांब वाटते,\nआणि जुन्या आठवणींची चादर\nमन पुन्हा पुन्हा ओढून घेते.",
            author = "यश",
            category = ShayariCategory.SAD.id,
            cardTheme = ShayariCardTheme.INDIGO_SAD.id,
            searchKeywords = "sad ashru tears ratra aathvani"
        ),

        // ==================== DOSTI (मैत्री) - 6 Marathi Shayaris ====================
        ShayariEntity(
            text = "रक्ताचं नातं नसलं तरी\nहृदयाची घट्ट गाठ असते,\nसंकटातही जी खंबीर उभी राहते\nतीच खरी जिवाभावाची मैत्री असते.",
            author = "यश",
            category = ShayariCategory.DOSTI.id,
            cardTheme = ShayariCardTheme.EMERALD_DOSTI.id,
            searchKeywords = "dosti maitri friend jivabhav sankat",
            isDailyFeatured = true
        ),
        ShayariEntity(
            text = "मित्र असे असावेत की\nबोलण्याआधीच मन ओळखावं,\nआणि दुःख कितीही मोठं असलं तरी\nत्यांच्या हसण्यात विरघळून जावं.",
            author = "यश",
            category = ShayariCategory.DOSTI.id,
            cardTheme = ShayariCardTheme.EMERALD_DOSTI.id,
            searchKeywords = "dosti maitri mitra friend hasya"
        ),
        ShayariEntity(
            text = "चहाची टपरी, संध्याकाळची वेळ\nआणि मित्रांची ती अखंड गप्पांची मैफल,\nयापुढे जगातली कोणतीच श्रीमंती\nकधीच मोठी वाटत नाही.",
            author = "यश",
            category = ShayariCategory.DOSTI.id,
            cardTheme = ShayariCardTheme.EMERALD_DOSTI.id,
            searchKeywords = "dosti maitri chaha tapari gappa mitra"
        ),
        ShayariEntity(
            text = "हजारो लोक भेटतात आयुष्यात\nपण खास काहीच असतात,\nजे अंतर वाढलं तरीही\nमनाच्या सर्वात जवळ असतात.",
            author = "यश",
            category = ShayariCategory.DOSTI.id,
            cardTheme = ShayariCardTheme.EMERALD_DOSTI.id,
            searchKeywords = "dosti maitri ayushya mitra antar"
        ),
        ShayariEntity(
            text = "मैत्री म्हणजे शब्दांचा खेळ नाही,\nती तर विश्वासाची सावली असते,\nजी उन्हातही थंडावा देते\nआणि पावसातही आधार बनते.",
            author = "यश",
            category = ShayariCategory.DOSTI.id,
            cardTheme = ShayariCardTheme.EMERALD_DOSTI.id,
            searchKeywords = "dosti maitri vishwas savali aadhar"
        ),
        ShayariEntity(
            text = "वेळ बदलली, शहरं बदलली\nपण आपली यारी तशीच राहिली,\nएका फोनवर आजही सगळी टोळी\nजुनीच मजा नव्याने जगली.",
            author = "यश",
            category = ShayariCategory.DOSTI.id,
            cardTheme = ShayariCardTheme.EMERALD_DOSTI.id,
            searchKeywords = "dosti maitri yaari toli phone"
        ),

        // ==================== ATTITUDE (स्वॅग / ॲटिट्यूड) - 6 Marathi Shayaris ====================
        ShayariEntity(
            text = "शांत राहिलो म्हणजे\nकमकुवत समजू नकोस,\nवादळ येण्यापूर्वीची शांतता\nकधीच हलक्यात घेऊ नकोस.",
            author = "यश",
            category = ShayariCategory.ATTITUDE.id,
            cardTheme = ShayariCardTheme.AMBER_ATTITUDE.id,
            searchKeywords = "attitude swag shant vadal power",
            isDailyFeatured = true
        ),
        ShayariEntity(
            text = "आम्ही कोणाच्या मागे धावत नाही\nआणि कोणाची नक्कल करत नाही,\nस्वतःच्या मेहनतीने बनवलेली ओळख\nकधीच कोणासमोर झुकत नाही.",
            author = "यश",
            category = ShayariCategory.ATTITUDE.id,
            cardTheme = ShayariCardTheme.AMBER_ATTITUDE.id,
            searchKeywords = "attitude swag olakh mehnat swabhimani"
        ),
        ShayariEntity(
            text = "जिंकण्याची जिद्द असेल तर\nअडचणी आपोआप छोट्या होतात,\nआणि स्वतःवर विश्वास असणाऱ्यांच्या\nयशाच्या चर्चा सगळीकडे होतात.",
            author = "यश",
            category = ShayariCategory.ATTITUDE.id,
            cardTheme = ShayariCardTheme.AMBER_ATTITUDE.id,
            searchKeywords = "attitude jidd yash success vishwas"
        ),
        ShayariEntity(
            text = "लोकांच्या बोलण्याने आम्ही\nआमचा मार्ग बदलत नाही,\nकारण सिंहाची चाल कधीच\nइतरांच्या सल्ल्याने ठरत नाही.",
            author = "यश",
            category = ShayariCategory.ATTITUDE.id,
            cardTheme = ShayariCardTheme.AMBER_ATTITUDE.id,
            searchKeywords = "attitude swag sinha lion marg"
        ),
        ShayariEntity(
            text = "किंमत वेळेची असते मित्रा,\nशब्द तर कोणीही फेकून जातो,\nजो शांतपणे उत्तर कामातून देतो\nतोच खरा इतिहास घडवून जातो.",
            author = "यश",
            category = ShayariCategory.ATTITUDE.id,
            cardTheme = ShayariCardTheme.AMBER_ATTITUDE.id,
            searchKeywords = "attitude swag itihas kaam vel"
        ),
        ShayariEntity(
            text = "आमचा रुबाब आणि स्वाभिमान\nकोणाच्या परवानगीवर नसतो,\nआम्ही जसे आहोत तसेच आहोत\nआमचा अंदाजच वेगळा असतो.",
            author = "यश",
            category = ShayariCategory.ATTITUDE.id,
            cardTheme = ShayariCardTheme.AMBER_ATTITUDE.id,
            searchKeywords = "attitude rubab swabhimani andaj swag"
        )
    )
}
