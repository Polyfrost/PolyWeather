plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    // Adding new versions/loaders can be done like so:
    // For each version, we add a new wrapper around the last from highest to lowest.
    // Each mod loader needs to link up to the previous version's mod loader so that the mappings can be processed from the previous version.
    // "1.12.2-forge"(11202, "srg") {
    //     "1.8.9-forge"(10809, "srg")
    // }

    "1.21.5-fabric"(12105, "yarn") {
        "1.21.5-neoforge"(12105, "srg") {
            "1.21.4-neoforge"(12104, "srg") {
                "1.21.4-fabric"(12104, "yarn") {
                    "1.21.3-fabric"(12103, "yarn") {
                        "1.21.3-neoforge"(12103, "srg") {
                            "1.21.2-neoforge"(12102, "srg") {
                                "1.21.2-fabric"(12102, "yarn") {
                                    "1.21.1-fabric"(12101, "yarn") {
                                        "1.21.1-neoforge"(12101, "srg") {
                                            "1.20.6-neoforge"(12006, "srg") {
                                                "1.20.6-fabric"(12006, "yarn") {
                                                    "1.20.4-fabric"(12004, "yarn") {
                                                        "1.20.4-forge"(12004, "srg") {
                                                            "1.20.1-forge"(12001, "srg") {
                                                                "1.20.1-fabric"(12001, "yarn") {
                                                                    "1.19.4-fabric"(11904, "yarn") {
                                                                        "1.19.4-forge"(11904, "srg") {
                                                                            "1.18.2-forge"(11802, "srg") {
                                                                                "1.18.2-fabric"(11802, "yarn") {
                                                                                    "1.17.1-fabric"(11701, "yarn") {
                                                                                        "1.17.1-forge"(11701, "srg") {
                                                                                            "1.16.5-forge"(11605, "srg") {
                                                                                                "1.16.5-fabric"(11605, "yarn") {
                                                                                                    "1.12.2-fabric"(11202, "yarn") {
                                                                                                        "1.12.2-forge"(11202, "srg") {
                                                                                                            "1.8.9-forge"(10809, "srg") {
                                                                                                                "1.8.9-fabric"(10809, "yarn")
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    strictExtraMappings.set(true)
}