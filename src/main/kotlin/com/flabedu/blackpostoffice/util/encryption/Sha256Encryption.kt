package com.flabedu.blackpostoffice.util.encryption

import org.springframework.stereotype.Component
import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Random
import kotlin.experimental.and

@Component
class Sha256Encryption : Encryption {

    private fun getEncryption(password: String, salt: String): String {

        val saltByte = salt.toByteArray()
        val temp = password.toByteArray()
        val bytes = ByteArray(saltByte.size + temp.size)
        var result = ""

        try {
            val messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM)
            messageDigest.update(bytes)

            val digest = messageDigest.digest()

            val encryptionBuilder = StringBuilder()

            for (digestByte in digest) {
                encryptionBuilder.append(
                    ((digestByte and HEXA_DECIMAL_EXPRESSION.toByte()) + HASH_VALUE_SIZE)
                        .toString(HEXA_DECIMAL)
                        .substring(DIGEST_BYTE_EXTRACT)
                )
            }

            result = encryptionBuilder.toString()

        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("알고리즘 MD5가 요구되었지만, 현재의 환경에서는 사용 가능하지 않습니다.", e)
        }

        return result
    }

    private fun getSalt(): String {

        val random = Random()
        val salt = ByteArray(SALT_LENGTH)
        val saltBuilder = StringBuilder()

        random.nextBytes(salt)

        for (saltByte in salt) {
            saltBuilder.append(String.format(HEX_VALUE, saltByte))
        }

        return saltBuilder.toString()
    }

    override fun encryption(plainText: String): String {

        return getEncryption(plainText, getSalt())

    }
}