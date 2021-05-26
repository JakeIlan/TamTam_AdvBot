package me.evgen.advbot.service

import me.evgen.advbot.db.dao.AdvertDaoImpl
import me.evgen.advbot.db.dao.TempAdvertDaoImpl
import me.evgen.advbot.model.entity.Advert
import me.evgen.advbot.model.entity.TempAdvert

object AdvertService {

    private val advertDao = AdvertDaoImpl()
    private val tempAdvertDao = TempAdvertDaoImpl()

    fun findAdvert(id: Long): Advert? {
        return advertDao.findAdvert(id)
    }

    fun addAdvert(advert: Advert) {
        advertDao.insert(advert)
    }

    fun updateAdvert(advert: Advert) {
        advertDao.update(advert)
    }

    fun deleteAdvert(advertId: Long) {
        advertDao.deleteAdvert(advertId)
    }

    fun findAdverts(userId: Long): List<Advert> {
        return advertDao.findAdverts(userId)
    }



    fun findTempAdvertByUserId(userId: Long): TempAdvert? {
        return tempAdvertDao.findByUserId(userId)
    }

    fun addTempAdvert(tempAdvert: TempAdvert) {
        tempAdvertDao.insert(tempAdvert)
    }

    fun deleteTempAdvertByUserId(userId: Long) {
        tempAdvertDao.deleteTempAdvert(userId)
    }
}
