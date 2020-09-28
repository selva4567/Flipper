package com.selvakumarsm.flipper.explore.data.db

import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.explore.domain.model.Place
import kotlinx.coroutines.delay
import javax.inject.Inject

class HangoutsCacheImpl @Inject constructor(): HangoutsCache {

    override suspend fun getCachedTrendingHangouts(): List<Place> {
        delay(3000)
        return listOf(
            Place("Golconda Fort").apply {
                about = "Hilltop Golconda Fort & Qutb Shahi tombs"
                description =
                    "Golconda Fort, also known as Golkonda is a fortified citadel and an early capital city of the Qutb Shahi dynasty, located in Hyderabad, Telangana, India. Because of the vicinity of diamond mines, especially Kollur Mine, Golconda flourished as a trade centre of large diamonds, known as the Golconda Diamonds."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.golconda
            },
            Place("Gachibowli").apply {
                about = "Gachibowli Stadium & upscale eateries"
                description =
                    "Quiet Gachibowli is a corporate district home to business parks with tech company offices, as well as campuses for the University of Hyderabad and other educational institutions. Lounge bars and upscale Indian and international restaurants cater to those who work in the area or live in nearby high-rise apartments. Gachibowli Stadium and GMC Balayogi Sports Complex host soccer, basketball, and other sports."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "5000032"
                drawableId = R.drawable.gachibowli
            },

            Place("Madhapur").apply {
                about = "Inorbit Mall & international dining"
                description =
                    "Madhapur is a busy corporate area home to business parks and the large Inorbit Mall, with high-end local and international brands and a food court. Indian and global restaurants and swanky bars on Hitech City Road cater to business travelers and local professionals. Surrounded by granite rocks, Durgam Cheruvu, or Secret Lake, has an artificial waterfall and floating fountain, and is popular for boating."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.madhapur
            }
        )
    }

    override suspend fun getCachedPopularHangouts(): List<Place> {
//        delay(3000)
        return listOf(
            Place("Golconda Fort").apply {
                about = "Hilltop Golconda Fort & Qutb Shahi tombs"
                description =
                    "Golconda Fort, also known as Golkonda is a fortified citadel and an early capital city of the Qutb Shahi dynasty, located in Hyderabad, Telangana, India. Because of the vicinity of diamond mines, especially Kollur Mine, Golconda flourished as a trade centre of large diamonds, known as the Golconda Diamonds."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.golconda
                distance = "0.2 mi"
            },
            Place("Gachibowli").apply {
                about = "Gachibowli Stadium & upscale eateries"
                description =
                    "Quiet Gachibowli is a corporate district home to business parks with tech company offices, as well as campuses for the University of Hyderabad and other educational institutions. Lounge bars and upscale Indian and international restaurants cater to those who work in the area or live in nearby high-rise apartments. Gachibowli Stadium and GMC Balayogi Sports Complex host soccer, basketball, and other sports."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "5000032"
                drawableId = R.drawable.gachibowli
                distance = "0.8 mi"
            },

            Place("Madhapur").apply {
                about = "Inorbit Mall & international dining"
                description =
                    "Madhapur is a busy corporate area home to business parks and the large Inorbit Mall, with high-end local and international brands and a food court. Indian and global restaurants and swanky bars on Hitech City Road cater to business travelers and local professionals. Surrounded by granite rocks, Durgam Cheruvu, or Secret Lake, has an artificial waterfall and floating fountain, and is popular for boating."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.madhapur
                distance = "1.1 mi"
            },
            Place("Abids").apply {
                about =
                    "Historic Commercial hub with markets, plus cricket & soccer at the Lal Bahadur Shastri Stadium"
                description =
                    "Abids is a major commercial center in Hyderabad, India. Abids is well known as it is the oldest and most famous business area in Hyderabad. The state government building TSFC, and the President Plaza Badeshah palace are located here."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.abids
                distance = "1.8 mi"
            },
            Place("Kachiguda").apply {
                about =
                    "Traditional area with ornate shrines like Achalgachh Jain Temple & busy Kachiguda Railway Station"
                description =
                    "Kachiguda is a locality in the city of Hyderabad and one of the old suburbs in Hyderabad, Telangana, India. The third largest railway station in Hyderabad, Kacheguda railway station, built during the Nizam rule, is a major landmark of Kachiguda."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "5000032"
                drawableId = R.drawable.kachiguda
                distance = "4.1 mi"
            },
            Place("Kukatpally").apply {
                about =
                    "Busy residential & commercial area with sari shops & global eateries, plus food tructs at IDL Lake"
                description =
                    "Kukatpally is a suburb located in north western part of Hyderabad in the Indian state of Telangana. It is the headquarters of Kukatpally mandal in Malkajgiri revenue division of Medchal-Malkajgiri district."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.kukatpally
                distance = "1.3 mi"
            },
            Place("HITEC City").apply {
                about = "Tech companies & Shiparamam village"
                description =
                    "HITEC City is a busy tech industry hub. It's filled with office parks and served by Indian, Italian, and BBQ restaurants, as well as lounge bars and fast-food outlets, clustered mainly around Hitech City Main Road. Shilparamam is a crafts village displaying and selling work by artisans from around India, and home to a sculpture park and museum. The Shilpakala Vedika convention venue also hosts music and comedy."
                city = "Hyderabad"
                state = "Telangana"
                pincode = "500008"
                drawableId = R.drawable.hitec
                distance = "7.7 mi"
            }
        )
    }
}