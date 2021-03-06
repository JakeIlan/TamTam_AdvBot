package me.evgen.advbot.model.state

import chat.tamtam.botsdk.client.RequestsManager
import chat.tamtam.botsdk.keyboard.keyboard
import chat.tamtam.botsdk.model.Button
import chat.tamtam.botsdk.model.ButtonType
import chat.tamtam.botsdk.model.request.InlineKeyboard
import chat.tamtam.botsdk.state.CallbackState
import me.evgen.advbot.getBackButton
import me.evgen.advbot.getUserId
import me.evgen.advbot.model.entity.Advert
import me.evgen.advbot.model.navigation.Payload
import me.evgen.advbot.service.UserService

class AdvListState(timestamp: Long) : BaseState(timestamp), CustomCallbackState {
    override suspend fun handle(callbackState: CallbackState, prevState: BaseState, requestsManager: RequestsManager) {
        val ads = UserService.findUser(callbackState.getUserId().id)?.advertList ?: listOf()

        val inlineKeyboard = createKeyboard(ads)

        if (ads.isNotEmpty()){
            "Ваши объявления:".answerWithKeyboard(callbackState.callback.callbackId, inlineKeyboard, requestsManager)
        } else {
            "Здесь будут отображаться ваши объявления".answerWithKeyboard(callbackState.callback.callbackId, inlineKeyboard, requestsManager)
        }
    }

    private fun createKeyboard(advSet: Collection<Advert>): InlineKeyboard {
        return keyboard {
            for (entry in advSet) {
                +buttonRow {
                    +Button(
                        ButtonType.CALLBACK,
                        entry.title,
                        payload = Payload(
                            AdvState::class,
                            AdvState(timestamp, entry.id).toJson()
                        ).toJson()
                    )
                }
            }
            +buttonRow {
                +getBackButton(
                    Payload(
                        MenuAdvertState::class, MenuAdvertState(
                            timestamp
                        ).toJson()
                    )
                )
            }
        }
    }
}
