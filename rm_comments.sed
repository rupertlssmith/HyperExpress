# Remove Java/C/C++ style comments from a file.
# To use, run: 'sed -f rm-comments.sed < inputfile'

# Uncomment next line to show // style comments in output
#s/\/\/ ?//g

# Single line comment style: //
# Delete the line that only has whitespace and the comment
#/^\( \|\t\)*\/\/.*$/ d
# Else remove just the comment and leave the rest
#s/\/\/.*$//g

# Multiple line comment style: /* comment */
# All the entire lines of a multiline comment will be deleted
# Therfore do not do this:
# <h1>My Title</h1>  /* This comment will span
#                     * multiple lines. And it
#                     * will cause the "My Title"
#                     * to be deleted.
#                     */
# Delete the line that only has whitespace and the comment
/^\( \|\t\)*\/\*.*\*\/\( \|\t\)*$/ d
# Else remove just the comment and the trailing space and leave the rest
s/\/\*.*\*\/$\? \?//g
# Finally get rid of those comments that span multiple lines
/^\( \|\t\)*\/\*.*/,/\*\// d

# Notes:
# This script will really *only* remove the comments, and sometimes the line,
# but any surrounding lines it will not touch, so bear that in mind.
# '\( \|\t\)*' matches tabs and spaces, and could also be replaced with ' *'
# This is done in case you have a comment that is indented with the rest of the text.
