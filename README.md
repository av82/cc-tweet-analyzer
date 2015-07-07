## Environment Description

The program implements two features:

1. Calculate the total number of times each word has been tweeted. 
2. Calculate the median number of unique words per tweet, and update this median as tweets come in.



The program is built with Java

There is a manager class (TweetManager.java)  that facilitates I/O operation for reading input file, write the output files, parse the tweets (lines).
The class also throws errors if there is an I/O error like file not found exception.

1. Feature 1:(WordCounter.java)  for each tweet (line) the words are split on spaces, as per the requirements / assumptions given. The words are pushed to a tree map to preserve ASCCI ordering along with the count per word. 

2. Feature 2:(UniqueMedian.java)  for each tweet the words are split and added to a set to produce a unique set of words in a tweet 
    1. The number of unique words is fed to method that computes median
    2. Two priority queues are used to quickly find the median as the number comes in 
    3. A left priority queue facilitates O(1) access to **biggest** number of all the **numbers that are LESSER than current          median** (MAX HEAP)
    4. A right priority queue facilitates O(1) access to **smallest** number of all the **numbers that are GREATER than current       median** (MIN HEAP)
    5. The queues are balanced based on the number of elements in each queue, such that they don't differ by more than 1 element
    6. When the total elements are odd, the head of right queue is the median, else if total elements are odd, median is average        of heads of right and left queues

The paths of input files, output files are relative to the manager class.

Program can be run with *./run.sh* command.
                
