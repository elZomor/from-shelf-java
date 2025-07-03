format:
	./gradlew spotlessApply

lint:
	./gradlew checkstyleMain checkstyleTest

checkall:
	./gradlew spotlessApply checkstyleMain checkstyleTest

