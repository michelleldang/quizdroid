package edu.uw.ischool.mldang.quizdroid
data class Question(
    val id: Int,
    val question: String,
    val one: String,
    val two: String,
    val three: String,
    val four: String,
    val correctAnswer: Int)

object SetStat{
    var correct: Int = 0
    var totalAnswered: Int = 0
    var totalQuestions: Int? = null
}

object Questions {
    fun getMathQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val q1 = Question(
            1, "What is 1+1?",
            "1", "2",
            "3", "4", 2
        )
        questionsList.add(q1)
        val q2 = Question(
            2, "What is 2+2",
            "1", "2",
            "3", "4", 4
        )
        questionsList.add(q2)
        return questionsList
    }

    fun getPhysicsQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val q1 = Question(
            1, "What is the formula for calculating kinetic energy?",
            "KE = 1/2(mv)^2", "KE = mgh",
            "KE = (mv)/t", "KE = 1/2 * mgt", 1
        )
        questionsList.add(q1)
        val q2 = Question(
            2, "What is the value of gravity?",
            "10 m/s^2", "5 m/s^2",
            "10.2 m/s^2", "9.8 m/s^2", 4
        )
        questionsList.add(q2)
        return questionsList
    }

    fun getMarvelQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val q1 = Question(
            1, "How many stones were in the infinite gauntlet",
            "5", "6",
            "7", "8", 2
        )
        questionsList.add(q1)
        val q2 = Question(
            2, "What is the name of the villain in End Game?",
            "Loki", "Ultron",
            "Green Goblin", "Thanos", 4
        )
        questionsList.add(q2)
        return questionsList
    }
}
