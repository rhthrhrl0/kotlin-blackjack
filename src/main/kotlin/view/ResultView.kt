package view

import domain.BlackJackCardDeck
import domain.Card
import domain.Dealer
import domain.GameResult
import domain.Participants
import domain.Player
import domain.Players

class ResultView {
    fun printGameInit(players: Players) {
        println(PRINT_GAME_INIT_MESSAGE.format(formatStringName(players)))
    }

    private fun formatStringName(players: Players): String {
        return players.players.joinToString(SEPARATOR) { it.name.name }
    }

    fun printInitCards(participants: Participants) {
        participants.forEach { participant ->
            println(PRINT_NAME_AND_CARDS.format(participant.name.name, formatStringCards(participant.showInitCards())))
        }
        println()
    }

    fun printPlayerCard(player: Player) {
        println(PRINT_NAME_AND_CARDS.format(player.name.name, formatStringCards(player.showAllCards())))
    }

    fun printDealerAddCard(dealer: Dealer) {
        println(PRINT_DEALER_ADD_CARD.format(dealer.name.name))
    }

    fun printGameResult(players: Players, dealer: Dealer) {
        println(PRINT_GAME_RESULT)
        formatStringDealerResult(players, dealer)
        formatStringPlayersResult(players, dealer)
    }

    private fun formatStringCards(cards: List<Card>): String {
        return cards.joinToString(SEPARATOR) { card ->
            card.info()
        }
    }

    private fun Card.info(): String {
        return "${cardNumber.number}${cardCategory.pattern}"
    }

    private fun formatStringDealerResult(players: Players, dealer: Dealer) {
        val dealerResult = dealer.getResult(players)
        println(
            PRINT_DEALER_GAME_RESULT.format(
                dealer.name.name,
                dealerResult[GameResult.WIN],
                GameResult.WIN.output,
                dealerResult[GameResult.LOSE],
                GameResult.LOSE.output
            )
        )
    }

    private fun formatStringPlayersResult(players: Players, dealer: Dealer) {
        players.players.forEach { player ->
            val playerResult = player.getGameResult(dealer)
            if (playerResult == GameResult.WIN)
                println(PRINT_PLAYER_GAME_RESULT.format(player.name.name, GameResult.WIN.output))
            if (playerResult == GameResult.LOSE)
                println(PRINT_PLAYER_GAME_RESULT.format(player.name.name, GameResult.LOSE.output))
        }
    }

    fun printScore(participants: Participants) {
        participants.participants.forEach { participant ->
            println(
                PRINT_NAME_AND_CARDS_AND_SCORE.format(
                    participant.name.name,
                    formatStringCards(participant.showAllCards()),
                    participant.resultSum()
                )
            )
        }
    }

    companion object {
        private const val PRINT_GAME_INIT_MESSAGE = "\n딜러와 %s에게 ${BlackJackCardDeck.DRAW_INIT_CARD_COUNT}장의 나누었습니다."
        private const val SEPARATOR = ", "
        private const val PRINT_NAME_AND_CARDS = "%s카드: %s"
        private const val PRINT_DEALER_ADD_CARD = "\n%s는 ${Dealer.DEALER_ADD_CARD_CONDITION}이하라 한장의 카드를 더 받았습니다."
        private const val PRINT_GAME_RESULT = "\n## 최종승패"
        private const val PRINT_DEALER_GAME_RESULT = "%s: %d%s %d%s"
        private const val PRINT_PLAYER_GAME_RESULT = "%s: %s"
        private const val PRINT_NAME_AND_CARDS_AND_SCORE = "%s 카드: %s - 결과: %d"
    }
}
