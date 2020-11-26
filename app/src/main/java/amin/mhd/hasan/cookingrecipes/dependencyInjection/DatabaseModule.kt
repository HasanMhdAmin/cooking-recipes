package amin.mhd.hasan.cookingrecipes.dependencyInjection

import amin.mhd.hasan.cookingrecipes.database.RecipesDao
import amin.mhd.hasan.cookingrecipes.database.RecipesDatabase
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by Hasan Mhd Amin on 25.11.20
 */

@Module
@InstallIn(ApplicationComponent::class)
public class DatabaseModule {

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(application: Application): RecipesDatabase {
            return Room.databaseBuilder(
                application,
                RecipesDatabase::class.java,
                "recipes_database"
            ).fallbackToDestructiveMigration().build()
        }

        @Provides
        @Singleton
        fun provideRecipesDao(recipesDatabase: RecipesDatabase): RecipesDao {
            return recipesDatabase.recipesDao()
        }
    }
}