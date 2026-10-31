package com.example.data

import com.example.model.*

object SampleData {

  val biologyQuestions = listOf(
    QuizQuestion(
      id = "bio_q1",
      type = QuestionType.MULTIPLE_CHOICE,
      prompt = "What is the primary hereditary material found in the nucleus of human cells?",
      somaliPrompt = "Waa maxay walaxda asaasiga ah ee xanbaarsan hiddo-wadaha ee ku dhex jirta bu'da unugga?",
      options = listOf("DNA (Deoxyribonucleic acid)", "RNA (Ribonucleic acid)", "Glucose", "Lipid"),
      correctAnswer = "DNA (Deoxyribonucleic acid)",
      explanation = "DNA waa walaxda asaasiga ah ee unugga oo wadata tilmaamaha hidde-sidaha dhamaan noolaha."
    ),
    QuizQuestion(
      id = "bio_q2",
      type = QuestionType.TRUE_FALSE,
      prompt = "Mendel's Law of Segregation states that alleles separate during gamete formation.",
      somaliPrompt = "Qaanuunka koowaad ee Mendel wuxuu sheegayaa in hidde-sideyaasha lammaanaha ahi ay kala tagaan xilliga taranka.",
      options = listOf("Sax (True)", "Khalad (False)"),
      correctAnswer = "Sax (True)",
      explanation = "Sax: Inta lagu jiro habka Meiosis, hidde-sideyaasha lamaanaha ahi way kala go'aan si ay u sameeyaan gametes gaar ah."
    ),
    QuizQuestion(
      id = "bio_q3",
      type = QuestionType.FILL_BLANKS,
      prompt = "Choose the correct missing word from the word bank to complete the biological statement:",
      somaliPrompt = "Dooro ereyga saxda ah ee ka maqan weedha hidde-wadaha:",
      blankSentence = "The outward observable physical expression of a genotype is called the ______ in genetics.",
      wordBank = listOf("phenotype", "chose", "allele", "common", "ribosome", "work"),
      correctAnswer = "phenotype",
      explanation = "Phenotype waa muqaalka dibadeed ee noolaha ee ka dhasha hidde-wadaha (Genotype) iyo deegaanka."
    ),
    QuizQuestion(
      id = "bio_q4",
      type = QuestionType.MULTIPLE_CHOICE,
      prompt = "Which of the following is considered the powerhouse of the cell?",
      somaliPrompt = "Xubinta unugga ee mas'uulka ka ah soo saarista tamarta (Powerhouse) waa kuwee?",
      options = listOf("Mitochondria", "Nucleus", "Ribosome", "Endoplasmic Reticulum"),
      correctAnswer = "Mitochondria",
      explanation = "Mitochondria waxay soo saartaa tamarta unugga ee loo yaqaan ATP iyada oo adeegsanaysa habka Cellular Respiration."
    ),
    QuizQuestion(
      id = "bio_q5",
      type = QuestionType.TRUE_FALSE,
      prompt = "Red blood cells possess a large nucleus when fully mature.",
      somaliPrompt = "Unugyada dhiigga cas ee qaangaarka ah waxay leeyihiin bu' (nucleus) weyn.",
      options = listOf("Sax (True)", "Khalad (False)"),
      correctAnswer = "Khalad (False)",
      explanation = "Khalad: Unugyada dhiigga cas ee bini'aadamka markay qaangaaraan ma laha bu' (nucleus) si ay boos ugu helaan hemoglobin-ta xanbaarta Ogsijiinta."
    ),
    QuizQuestion(
      id = "bio_q6",
      type = QuestionType.FILL_BLANKS,
      prompt = "Tap the correct term to fill the blank:",
      somaliPrompt = "Taabo ereyga saxda ah ee buuxinaya meesha banaan:",
      blankSentence = "Photosynthesis requires sunlight, water, and carbon ______ to synthesize glucose.",
      wordBank = listOf("dioxide", "monoxide", "nitrogen", "water", "oxygen", "sugar"),
      correctAnswer = "dioxide",
      explanation = "Dhirta cagaaran waxay u baahan yihiin Carbon Dioxide iyo biyaha si ay u sameeyaan sokor (glucose) xilliga Photosynthesis."
    )
  )

  val physicsQuestions = listOf(
    QuizQuestion(
      id = "phy_q1",
      type = QuestionType.MULTIPLE_CHOICE,
      prompt = "According to Newton's Second Law of Motion, Force equals:",
      somaliPrompt = "Qaanuunka labaad ee Newton ee socodka, Xooggu (Force) wuxuu u dhigmaa:",
      options = listOf("Mass × Acceleration (F = m × a)", "Mass ÷ Velocity", "Work × Time", "Mass × Gravity²"),
      correctAnswer = "Mass × Acceleration (F = m × a)",
      explanation = "Newton's Second Law: Xooggu waa taranka cufka iyo xawaare-kordhinta walaxda (F = m · a)."
    ),
    QuizQuestion(
      id = "phy_q2",
      type = QuestionType.TRUE_FALSE,
      prompt = "Sound waves can travel freely through a complete vacuum.",
      somaliPrompt = "Mowjadaha dhawaaqu waxay si xor ah ugu dhex socon karaan meel banaan (vacuum) oo aan hawo lahayn.",
      options = listOf("Sax (True)", "Khalad (False)"),
      correctAnswer = "Khalad (False)",
      explanation = "Khalad: Dhawaaqu waa mawjad makaanikada ah (Mechanical wave) oo wuxuu u baahan yahay maaddo ama hawo uu ku dhex socdo."
    ),
    QuizQuestion(
      id = "phy_q3",
      type = QuestionType.FILL_BLANKS,
      prompt = "Fill the missing unit:",
      somaliPrompt = "Ereyga halbeegga saxda ah buuxi:",
      blankSentence = "The SI unit of electrical resistance measured in circuits is ______.",
      wordBank = listOf("ohm", "watt", "volt", "ampere", "joule", "kelvin"),
      correctAnswer = "ohm",
      explanation = "Halbeega caalamiga ah ee cabbiraadda iska-caabinta korontada (Resistance) waa Ohm (Ω)."
    )
  )

  val chemistryQuestions = listOf(
    QuizQuestion(
      id = "chem_q1",
      type = QuestionType.MULTIPLE_CHOICE,
      prompt = "What is the simplest hydrocarbon alkane with the molecular formula CH₄?",
      somaliPrompt = "Waa maxay gaaska ugu fudud ee hydrocarbon ah oo qaacidadiisu tahay CH₄?",
      options = listOf("Methane", "Ethane", "Propane", "Butane"),
      correctAnswer = "Methane",
      explanation = "Methane (CH₄) waa xubinta ugu fudud uguna horeysa ee qoyska Alkanes."
    ),
    QuizQuestion(
      id = "chem_q2",
      type = QuestionType.TRUE_FALSE,
      prompt = "Acids have a pH value greater than 7 on the standard scale.",
      somaliPrompt = "Aashitooyinka (Acids) heerka pH-kooda wuxuu ka sarreeyaa 7.",
      options = listOf("Sax (True)", "Khalad (False)"),
      correctAnswer = "Khalad (False)",
      explanation = "Khalad: Maadada aashitada ah pH-keedu wuxuu ka hooseeyaa 7 (pH < 7), halka salalka (bases) ka sarreeyaan 7."
    ),
    QuizQuestion(
      id = "chem_q3",
      type = QuestionType.FILL_BLANKS,
      prompt = "Complete the chemical statement:",
      somaliPrompt = "Dooro ereyga saxda ah ee kimistariga:",
      blankSentence = "Water molecule consists of two atoms of hydrogen and one atom of ______.",
      wordBank = listOf("oxygen", "carbon", "helium", "iron", "copper", "salt"),
      correctAnswer = "oxygen",
      explanation = "Biyaha qaacidoodu waa H₂O (laba atom oo hydrogen ah iyo hal atom oo oxygen ah)."
    )
  )

  val samplePdfs = listOf(
    PdfDocument(
      id = "pdf_bio_c3",
      title = "Biology: Genetics & Heredity (Fasalka 4-aad)",
      subject = "Biology",
      chapter = "Chapter 3",
      pages = 28,
      isAnnotated = true,
      iconName = "biotech",
      questions = biologyQuestions
    ),
    PdfDocument(
      id = "pdf_phy_c2",
      title = "Physics: Mechanics & Newton's Laws",
      subject = "Physics",
      chapter = "Chapter 2",
      pages = 34,
      isAnnotated = true,
      iconName = "precision_manufacturing",
      questions = physicsQuestions
    ),
    PdfDocument(
      id = "pdf_chem_c4",
      title = "Chemistry: Hydrocarbons & Organic Compounds",
      subject = "Chemistry",
      chapter = "Chapter 4",
      pages = 22,
      isAnnotated = true,
      iconName = "science",
      questions = chemistryQuestions
    ),
    PdfDocument(
      id = "pdf_math_c1",
      title = "Mathematics: Quadratic Equations & Matrices",
      subject = "Math",
      chapter = "Unit 1 & 2",
      pages = 40,
      isAnnotated = true,
      iconName = "calculate",
      questions = biologyQuestions // reusable fallback
    ),
    PdfDocument(
      id = "pdf_som_c1",
      title = "Af-Soomaali: Suugaanta, Gabayada & Naxwaha",
      subject = "Somali Lang",
      chapter = "Cutubka 1-aad",
      pages = 18,
      isAnnotated = true,
      iconName = "auto_stories",
      questions = biologyQuestions
    ),
    PdfDocument(
      id = "pdf_hist_c2",
      title = "History: Halgameyaashii SYL & Madaxbannaanida",
      subject = "History",
      chapter = "Cutubka 3-aad",
      pages = 25,
      isAnnotated = true,
      iconName = "history_edu",
      questions = biologyQuestions
    ),
    PdfDocument(
      id = "pdf_geo_c1",
      title = "Geography: Geomorphology & Somali Coastal Zones",
      subject = "Geography",
      chapter = "Unit 4",
      pages = 19,
      isAnnotated = true,
      iconName = "public",
      questions = biologyQuestions
    ),
    PdfDocument(
      id = "pdf_islam_c1",
      title = "Islamic Studies: Fiqhi & Tafsiir (Form 4)",
      subject = "Islamic",
      chapter = "Cutubka 2-aad",
      pages = 26,
      isAnnotated = true,
      iconName = "menu_book",
      questions = biologyQuestions
    )
  )

  val subjectProgressList = listOf(
    SubjectProgress("MATH", 0.60f, 0xFF1E88E5, "calculate", 20, 12),
    SubjectProgress("PHYSICS", 0.75f, 0xFF00C853, "bolt", 16, 12),
    SubjectProgress("BIOLOGY", 0.40f, 0xFF00ACC1, "biotech", 15, 6),
    SubjectProgress("CHEMISTRY", 0.35f, 0xFF8E24AA, "science", 20, 7),
    SubjectProgress("SOMALI", 0.85f, 0xFFFF8F00, "auto_stories", 14, 12),
    SubjectProgress("HISTORY", 0.70f, 0xFFD81B60, "history_edu", 10, 7),
    SubjectProgress("GEOGRAPHY", 0.50f, 0xFF43A047, "public", 12, 6)
  )

  val scoreLeaderboard = listOf(
    LeaderboardUser(1, "Abdikadir Nur", "@abdikadir_so", "21 Oktoobar Sec, Mogadishu", 1250, 14, "👑 Gold Champion", isCurrentUser = true),
    LeaderboardUser(2, "Fatima Omar Ali", "@fatima_ali", "Gambool High, Garowe", 1180, 19, "🥈 Silver Scholar"),
    LeaderboardUser(3, "Liban Mohamed Hassan", "@liban_mh", "Noradin Girls/Boys, Hargeisa", 1120, 11, "🥉 Bronze Achiever"),
    LeaderboardUser(4, "Asha Abdi Warsame", "@asha_w", "Banaadir Academy, Mogadishu", 1040, 9, "⭐ Star Student"),
    LeaderboardUser(5, "Khadar Ismail Dirie", "@khadar_d", "Kismayo Secondary, Kismayo", 990, 8, "⚡ Quick Thinker"),
    LeaderboardUser(6, "Hodan Yusuf Gedi", "@hodan_y", "Dhusamareb Model School", 940, 7, "📚 Study Master"),
    LeaderboardUser(7, "Mustafe Farah", "@mustafe_f", "Baidoa High School", 890, 5, "🎯 High Scorer")
  )

  val streakLeaderboard = listOf(
    LeaderboardUser(1, "Fatima Omar Ali", "@fatima_ali", "Gambool High, Garowe", 1180, 28, "🔥 28-Day Streak Master"),
    LeaderboardUser(2, "Abdikadir Nur", "@abdikadir_so", "21 Oktoobar Sec, Mogadishu", 1250, 14, "🔥 14-Day Streak", isCurrentUser = true),
    LeaderboardUser(3, "Liban Mohamed Hassan", "@liban_mh", "Noradin Schools, Hargeisa", 1120, 13, "🔥 13-Day Streak"),
    LeaderboardUser(4, "Khadar Ismail Dirie", "@khadar_d", "Kismayo Secondary, Kismayo", 990, 12, "🔥 12-Day Streak"),
    LeaderboardUser(5, "Asha Abdi Warsame", "@asha_w", "Banaadir Academy, Mogadishu", 1040, 10, "🔥 10-Day Streak"),
    LeaderboardUser(6, "Shuceyb Junior", "@shuceyb_fan", "Mogadishu High", 870, 9, "🔥 9-Day Streak")
  )

  val initialRooms = listOf(
    StudyRoom("r1", "Biology Form 4 Finals", "Biology", "Abdikadir Nur", 8, 15, false, "", true),
    StudyRoom("r2", "Physics Past Papers (2020-2025)", "Physics", "Fatima Omar", 9, 15, true, "1234", true),
    StudyRoom("r3", "Math Genius Challenge", "Mathematics", "Liban Hassan", 12, 15, false, "", true),
    StudyRoom("r4", "Chemistry Organic Quick-Fire", "Chemistry", "Asha Warsame", 5, 15, false, "", true),
    StudyRoom("r5", "Taariikhda & Af-Soomaaliga", "Somali & History", "Mustafe Farah", 11, 15, false, "", true)
  )

  val initialBattlePeers = listOf(
    BattleParticipant("p1", "Abdikadir Nur (You)", "21 Oktoobar, Mogadishu", 0, 0, 0xFF0D47A1, isUser = true),
    BattleParticipant("p2", "Fatima Omar", "Gambool, Garowe", 0, 0, 0xFF00897B),
    BattleParticipant("p3", "Liban Mohamed", "Noradin, Hargeisa", 0, 0, 0xFFE53935),
    BattleParticipant("p4", "Asha Abdi", "Banaadir Academy", 0, 0, 0xFF8E24AA),
    BattleParticipant("p5", "Khadar Ismail", "Kismayo High", 0, 0, 0xFFFB8C00),
    BattleParticipant("p6", "Hodan Yusuf", "Dhusamareb Model", 0, 0, 0xFF00ACC1),
    BattleParticipant("p7", "Mustafe Farah", "Baidoa High", 0, 0, 0xFF3949AB),
    BattleParticipant("p8", "Yasmin Ali", "Galkacyo Secondary", 0, 0, 0xFFD81B60),
    BattleParticipant("p9", "Hamza Shire", "Bosaso Academy", 0, 0, 0xFF43A047),
    BattleParticipant("p10", "Zahra Hassan", "Mogadishu Star", 0, 0, 0xFF5E35B1),
    BattleParticipant("p11", "Farhan Dahir", "Burao High School", 0, 0, 0xFF00897B),
    BattleParticipant("p12", "Ikram Abdullahi", "Jowhar Model", 0, 0, 0xFFF4511E),
    BattleParticipant("p13", "Faisal Jama", "Beletweyne Sec", 0, 0, 0xFF6D4C41),
    BattleParticipant("p14", "Safia Said", "Afgooye Science High", 0, 0, 0xFF1E88E5),
    BattleParticipant("p15", "Guled Ahmed", "Qardho Academy", 0, 0, 0xFF7CB342)
  )

  val initialChatMessages = listOf(
    ChatMessage(
      id = "m1",
      senderName = "Fatima Omar",
      senderUsername = "@fatima_ali",
      text = "Asc ardayda! Waxaan maanta dib u eegayey Biology Chapter 3 (Genetics). Su'aasha ku saabsan Mendel's Laws yaa sharraxaad fiican ka bixin kara?",
      timestamp = "10:15 AM",
      badge = "🥈 Silver Scholar",
      subjectTag = "Biology"
    ),
    ChatMessage(
      id = "m2",
      senderName = "Abdikadir Nur",
      senderUsername = "@abdikadir_so",
      text = "Wcs Fatima! Mendel's First Law (Law of Segregation) wuxuu sheegayaa in labada allele ee dabeecadda ay kala tagaan xilliga meiosis, hal allele-na uu u gudbo gamete kasta.",
      timestamp = "10:18 AM",
      badge = "👑 Gold Champion",
      subjectTag = "Biology",
      isCurrentUser = true
    ),
    ChatMessage(
      id = "m3",
      senderName = "Liban Mohamed",
      senderUsername = "@liban_mh",
      text = "Masha Allah, aad ayaad u mahadsan tahay Abdikadir! Physics-ka qeybta Newton's Laws yaa diyaar u ah inaan 15-Player Battle ku galno Room 2?",
      timestamp = "10:22 AM",
      badge = "🥉 Bronze Achiever",
      subjectTag = "Physics"
    ),
    ChatMessage(
      id = "m4",
      senderName = "Asha Abdi",
      senderUsername = "@asha_w",
      text = "Anigu waan diyaar ahay! Aynu ku biirno 'Math Genius Challenge' sidoo kale. App-kan PDF Quiz generator-kiisu su'aalo cajiib ah ayuu soo saaraa!",
      timestamp = "10:25 AM",
      badge = "⭐ Star Student",
      subjectTag = "General"
    ),
    ChatMessage(
      id = "m5",
      senderName = "Khadar Ismail",
      senderUsername = "@khadar_d",
      text = "Dhibcaha maanta aad bay u sareeyaan! Hambalyo Abdikadir oo hoggaanka u haya Leaderboard-ka 👏",
      timestamp = "10:30 AM",
      badge = "⚡ Quick Thinker",
      subjectTag = "Ranking"
    )
  )
}
