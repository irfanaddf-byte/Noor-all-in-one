package com.example

import com.example.data.repository.NamesAndKidsCatalog
import com.example.data.repository.NoorDataCatalog
import com.example.data.repository.QuranCatalog
import com.example.util.IslamicDateHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun verifyCatalogCompleteness() {
        assertEquals(114, QuranCatalog.all114Surahs.size)
        assertEquals(99, NamesAndKidsCatalog.all99Names.size)
        assertEquals(20, NoorDataCatalog.hadiths.size)
        assertEquals(15, NoorDataCatalog.cities.size)
        assertEquals(20, NoorDataCatalog.wallpapers.size)
        assertEquals(10, NoorDataCatalog.naatsAndBayans.size)
        assertEquals(10, NoorDataCatalog.faqs.size)
        assertEquals(6, NamesAndKidsCatalog.sixKalmas.size)
        assertEquals(10, NamesAndKidsCatalog.kidsDuas.size)
        assertEquals(2, NamesAndKidsCatalog.prophetStories.size)
        assertTrue(NoorDataCatalog.duas.size >= 16)
    }

    @Test
    fun verifyThakurdwaraTimesAndZakat() {
        val tkd = NoorDataCatalog.cities.first()
        assertEquals("Thakurdwara", tkd.name)
        assertEquals("05:23", tkd.fajr)
        assertEquals("12:05", tkd.dhuhr)
        assertEquals("15:30", tkd.asr)
        assertEquals("17:18", tkd.maghrib)
        assertEquals("18:45", tkd.isha)

        val notFarz = IslamicDateHelper.calculateZakat(0.0, 0.0, 10000.0, 5000.0)
        assertFalse(notFarz.isFarz)

        val isFarz = IslamicDateHelper.calculateZakat(8.0, 0.0, 100000.0, 0.0)
        assertTrue(isFarz.isFarz)
        assertTrue(isFarz.zakatAmount > 0.0)
    }
}
