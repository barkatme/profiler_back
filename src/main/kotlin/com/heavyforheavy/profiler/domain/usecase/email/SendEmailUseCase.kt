package com.heavyforheavy.profiler.domain.usecase.email

import com.heavyforheavy.profiler.domain.usecase.Action
import com.heavyforheavy.profiler.domain.usecase.Result
import com.heavyforheavy.profiler.domain.usecase.UseCase
import com.heavyforheavy.profiler.infrastructure.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

class SendEmailUseCase : UseCase<SendEmailAction, SendEmailResult> {
  override suspend fun invoke(action: SendEmailAction): SendEmailResult =
    withContext(Dispatchers.IO) {

      val fromAddress = "profiler.developer@gmail.com"
      val password = "ooeqrjtozxurwvyn"

      SimpleEmail().apply {
        hostName = "smtp.gmail.com"
        setSmtpPort(465)
        setAuthenticator(DefaultAuthenticator(fromAddress, password))
        isSSLOnConnect = true
        setFrom(fromAddress)
        subject = action.subject
        setMsg(action.text)
        addTo(action.targetUser.email)
        send()
      }
      SendEmailResult(true)
    }
}

data class SendEmailAction(val subject: String, val text: String, val targetUser: User) : Action

data class SendEmailResult(val isSent: Boolean) : Result