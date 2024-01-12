package cinema

fun createCinemaSeatsPlan(): MutableList<MutableList<Any>> {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()
    val seatsPlan = mutableListOf<MutableList<Any>>(MutableList(seats) { it + 1 })
    repeat(rows) {
        seatsPlan.add(MutableList(seats) { 'S' })
    }
    return seatsPlan
}

fun printMenu() {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun printPlan(plan: MutableList<MutableList<Any>>) {
    println()
    println("Cinema:")
    println("  ${plan[0].joinToString(" ")}")
    for (i in 1 until plan.size) {
        println("$i ${plan[i].joinToString(" ")}")
    }
}

fun buyTicket(plan: MutableList<MutableList<Any>>) {
    val seats = plan[0].size
    val rows = plan.size - 1
    val frontRowPrice = 10
    val backRowPrice = if (seats * rows <= 60) 10 else 8
    val frontRows = rows / 2

    println()
    println("Enter a row number:")
    val row = readln().toInt()
    println("Enter a seat number in that row:")
    val seat = readln().toInt()

    when {
        row > rows || seat > seats -> {
            println()
            println("Wrong input!")
            buyTicket(plan)
        }

        plan[row][seat - 1] == 'B' -> {
            println()
            println("That ticket has already been purchased!")
            buyTicket(plan)
        }

        else -> {
            val ticketPrice = if (row <= frontRows) frontRowPrice else backRowPrice
            println()
            println("Ticket price: $$ticketPrice")
            plan[row][seat - 1] = 'B'
        }
    }
}

fun stats(plan: MutableList<MutableList<Any>>) {
    val seats = plan[0].size
    val rows = plan.size - 1
    val frontRowPrice = 10
    val backRowPrice = if (seats * rows <= 60) 10 else 8
    val frontRows = rows / 2

    val totalTickets = rows * seats
    var purchasedTickets = 0
    var currentIncome = 0
    var totalIncome = 0
    for (i in 1..rows) {
        repeat(seats) { seatIndex ->
            val seat = plan[i][seatIndex]
            val ticketPrice = if (i <= frontRows) frontRowPrice else backRowPrice
            totalIncome += ticketPrice
            if (seat == 'B') {
                purchasedTickets++
                currentIncome += ticketPrice
            }
        }
    }

    val percentage = (purchasedTickets / totalTickets.toDouble()) * 100
    val formatPercentage = "%.2f".format(percentage)

    println()
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun main() {
    val seatsPlan = createCinemaSeatsPlan()

    printMenu()
    var command = readln().toInt()
    while (command != 0) {
        when (command) {
            1 -> printPlan(seatsPlan)
            2 -> buyTicket(seatsPlan)
            3 -> stats(seatsPlan)
        }
        printMenu()
        command = readln().toInt()
    }
}