# Batch : Number Increment Batch Processor
In this project, I have created a batch file to run the java program. And later I will test this batch in diffent technologes.

# Getting Started



## Prerequisites

Make sure you have the following installed before running the code:
- [Java SE Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 11 or higher
- A text file containing numeric values (one number per line)


## Build/Complile the code

To build the code, you can use the following command in the terminal(PowerShell)

```bash 
javac -d bin (Get-ChildItem -Path src -Filter *.java -Recurse).FullName
```

## Run the code

To run the code, you can use the following command in the terminal:

```bash
java -cp bin eu.batch.custombatches.App path/to/your/input/file.txt
```

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.



## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).




## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


## Author
- **Name**: [Mehdi](https://github.com/elmahdi43)

## Version
- **Version**: 1.0.0



# Made by love ❤️
