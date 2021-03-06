package me.evgen.advbot.model.state

import chat.tamtam.botsdk.client.RequestsManager
import chat.tamtam.botsdk.keyboard.keyboard
import chat.tamtam.botsdk.model.Button
import chat.tamtam.botsdk.model.ButtonType
import chat.tamtam.botsdk.model.prepared.Chat
import chat.tamtam.botsdk.model.request.InlineKeyboard
import chat.tamtam.botsdk.state.CallbackState
import me.evgen.advbot.Payloads
import me.evgen.advbot.db.DBSessionFactoryUtil
import me.evgen.advbot.model.navigation.Payload
import me.evgen.advbot.getBackButton
import me.evgen.advbot.getUserId

class PlatformListState(timestamp: Long) : BaseState(timestamp), CustomCallbackState {
    override suspend fun handle(callbackState: CallbackState, prevState: BaseState, requestsManager: RequestsManager) {
        val chats = DBSessionFactoryUtil.localStorage.getChats(callbackState.getUserId())

        //TODO: добавть вкл/выкл и теги
        val inlineKeyboard = createKeyboard(chats)

        """Выберите платформу для настройки размещения.
            |Для того, чтобы платформа появилась в списке, добавьте бота @AdvertizerBot в свой чат или канал.
        """.trimMargin().answerWithKeyboard(
            callbackState.callback.callbackId,
            inlineKeyboard,
            requestsManager
        )
    }

    private fun createKeyboard(chatSet: Set<Chat>): InlineKeyboard {
        return keyboard {
            for (entry in chatSet) {
                +buttonRow {
                    +Button(
                        ButtonType.CALLBACK,
                        entry.title,
                        payload = Payload(
                            PlatformSettingsState::class,
                            PlatformSettingsState(timestamp, entry.chatId.id).toJson()
                        ).toJson()
                    )
                }
            }
            +buttonRow {
                +getBackButton(
                    Payload(
                        StartState::class, StartState(
                            timestamp
                        ).toJson()
                    )
                )
            }
        }
    }
}
