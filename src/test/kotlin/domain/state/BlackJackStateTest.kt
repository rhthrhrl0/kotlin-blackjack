package domain.state

import domain.card.Card
import domain.card.CardCategory
import domain.card.CardNumber
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import state.BlackJackState

class BlackJackStateTest {
    @Test
    fun `블랙잭 상태가 된다면 next를 호출하면 예외가 발생한다`() {
        val blackJackState = BlackJackState(
            Card.of(CardCategory.CLOVER, CardNumber.ACE),
            Card.of(CardCategory.CLOVER, CardNumber.JACK)
        )
        assertThrows<IllegalStateException> { blackJackState.draw(Card.of(CardCategory.CLOVER, CardNumber.NINE)) }
    }
}
