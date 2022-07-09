package data.repositoryImpl

import data.graphData.DataGraphLocator
import models.interactor.GraphEditorInteractorImpl

val GraphDataStorageInteractor = GraphEditorInteractorImpl(GraphRepositoryImpl(DataGraphLocator.graph))