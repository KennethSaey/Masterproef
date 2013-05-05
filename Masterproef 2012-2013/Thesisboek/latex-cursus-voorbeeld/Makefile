# The files to watch:

# The master file of the document
ROOTFILE            = lc-latex-cursus

# All the (other) .tex files
SATELITFILES	    = *.tex

# make a pdf
.PHONY : pdf
pdf : $(ROOTFILE).pdf

# make rootfile.pdf
$(ROOTFILE).pdf : $(SATELITFILES)
	pdflatex $(ROOTFILE).tex

# make bibliogrphy
.PHONY : bib
bib :	
	bibtex $(ROOTFILE)

# make index
.PHONY : index
index :	
	makeindex $(ROOTFILE)

# make the full document
.PHONY : full
full : 
	make clean
	pdflatex $(ROOTFILE).tex
	bibtex $(ROOTFILE)
	pdflatex $(ROOTFILE).tex
	pdflatex $(ROOTFILE).tex
	pdflatex $(ROOTFILE).tex
	makeindex $(ROOTFILE)
	pdflatex $(ROOTFILE).tex
	pdflatex $(ROOTFILE).tex
	pdflatex $(ROOTFILE).tex

# remove the temporary files
.PHONY : clean
clean :
	rm -f *.aux
	rm -f *.log
	rm -f *~
	rm -f *.toc
	rm -f *.bbl
	rm -f *.blg
	rm -f *.bak
	rm -f .*.swp
	rm -f *.idx
	rm -f *.ilg
	rm -f *.ind
	rm -f *.out
	
# remove everything but the sourcefiles
.PHONY : cleanfull
cleanfull :
	make clean
	rm -f *.pdf
	rm -f *.ps
	rm -f *.dvi



# Rommel:

# Niet gebruiken: een ps maken
$(ROOTFILE).ps : $(ROOTFILE).dvi
	dvips $(ROOTFILE).dvi -o $(ROOTFILE).ps

# Niet gebruiken: een dvi maken
$(ROOTFILE).dvi : $(SATELITFILES)
	latex $(ROOTFILE).tex


