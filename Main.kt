package cinema

import java.lang.IndexOutOfBoundsException

const val SIXTY: Int = 60
const val TEN: Int = 10
const val EIGHT: Int = 8
const val ONE: Int = 1
const val TWO: Int = 2
const val THREE: Int = 3
const val HUNDRED: Int = 100

fun main() {
    val cinema: MutableList<MutableList<Char>> = createCinema()
    var userInput: Int

    do {
        displayMenuOptions()

        userInput = readln().toInt()

        when (userInput) {
            ONE -> displayCinema(cinema)
            TWO -> buyTicket(cinema)
            THREE -> displayCinemaStatistics(cinema)
        }

    } while (userInput != 0)
}

/*
 * Prints cinema
 */
fun displayCinema(cinema: MutableList<MutableList<Char>>) {
    println("\nCinema:")
    print("  ")
    for (i in cinema[0].indices) {
        print("${i + 1} ")
    }

    print("\n")

    for (i in cinema.indices) {
        print("${i + 1} ")

        for (y in cinema[i]) {
            print("$y ")
        }

        print("\n")
    }

    print('\n')
}

/*
 * Create cinema
 */
fun createCinema(): MutableList<MutableList<Char>> {
    println("Enter the number of rows:")

    val numberOfRows: Int = readln().toInt()

    println("Enter the number of seats in each row:")

    val numberOfSeatsInEachRow: Int = readln().toInt()
    val cinema: MutableList<MutableList<Char>> = mutableListOf()

    for (i in 0 until numberOfRows) {
        cinema.add(mutableListOf())

        for (row in 0 until numberOfSeatsInEachRow) {
            cinema[i].add('S')
        }
    }

    print('\n')

    return cinema
}

/*
 * Display menu options
 */
fun displayMenuOptions() {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

/*
 *  Output ticket price information
 */
fun buyTicket(cinema: MutableList<MutableList<Char>>) {
    val numberOfRows: Int = cinema.size
    val numberOfSeatsInEachRow: Int = cinema[0].size
    var rowNumber: Int = 0
    var seatNumber: Int = 0
    var seatAvailable: Boolean = false

    do {
        println("Enter a row number:")
        rowNumber = readln().toInt() - 1

        println("Enter a seat number in that row:")
        seatNumber = readln().toInt() - 1

        try {
            if (cinema[rowNumber][seatNumber] == 'B') {
                println("That ticket has already been purchased!\n")
            } else {
                seatAvailable = true
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        }
    } while (!seatAvailable)

    val currentTicket: Int = (rowNumber + 1) * numberOfRows - (numberOfSeatsInEachRow - (seatNumber + 1))
    val ticketPrice: Int = getTicketPrice(cinema, currentTicket)

    println("\nTicket price: $$ticketPrice\n")

    cinema[rowNumber][seatNumber] = 'B'
}

/*
 * Displays:
 * The number of purchased tickets
 * The number of purchased tickets represented as a percentage. Percentages should be rounded to 2 decimal places
 * Current income
 * The total income that shows how much money the theatre will get if all the tickets are sold
 */
fun displayCinemaStatistics(cinema: MutableList<MutableList<Char>>) {
    val allTickets: Int = cinema.size * cinema[0].size
    var numberOfPutchasedTickets: Int = 0
    var currentIncome: Int = 0
    var totalIncome: Int = 0

    for (i in cinema.indices) {
        for (y in cinema[i].indices) {
            val ticketPrice: Int = getTicketPrice(cinema, (i + 1) * cinema.size - (cinema[0].size - y - 1))
            totalIncome += ticketPrice

            if (cinema[i][y] == 'B') {
                numberOfPutchasedTickets++
                currentIncome += ticketPrice
            }
        }
    }

    val percentage = (numberOfPutchasedTickets.toDouble() * HUNDRED) / allTickets.toDouble()

    println("Number of purchased tickets: $numberOfPutchasedTickets")
    println("Percentage: ${"%.2f".format(percentage)}%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome\n")
}

/*
 * Get ticket price
 */
fun getTicketPrice(cinema: MutableList<MutableList<Char>>, currentTicket: Int): Int {
    println(currentTicket)
    val numberOfRows = cinema.size
    val numberOfSeatsInEachRow = cinema[0].size
    val firstHalf: Int = numberOfRows / 2 * numberOfSeatsInEachRow

    return if (numberOfRows * numberOfSeatsInEachRow <= SIXTY) {
        TEN
    } else {
        when (currentTicket) {
            in 1..firstHalf -> TEN
            else -> EIGHT
        }
    }
}
