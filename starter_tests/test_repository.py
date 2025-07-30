"""
basic repository and report integrity checks.

These can be run with

  pytest -x -v starter_tests

The options mean:

- `-x | --exitfirst` - exit after the first test failure
- `-v` - show verbose output
"""

# pylint: disable=missing-function-docstring, redefined-outer-name

import re
import shutil
import subprocess
from pathlib import Path

import pytest

REPORT_FILENAME = "project-phase1-report.md"
PANDOC_EXE = "pandoc"
WEASYPRINT_EXE = "weasyprint"
MDLINT_EXE = "pymarkdownlnt"

###
# fixtures

@pytest.fixture
def pandoc_available():
  "ensure pandoc is on the PATH"
  if shutil.which(PANDOC_EXE) is None:
    pytest.fail(f"{PANDOC_EXE} is not installed or not on PATH - cannot run tests requiring it.\n" +
                "install with e.g. sudo apt install pandoc")

@pytest.fixture
def weasyprint_available():
  "ensure weasyprint is on the PATH"
  if shutil.which(WEASYPRINT_EXE) is None:
    pytest.fail(f"{WEASYPRINT_EXE} is not installed or not on PATH - cannot run tests requiring it.\n" +
                "install with e.g. sudo apt install weasyprint")


@pytest.fixture
def md_linter_available():
  "ensure pymarkdownlnt is on the PATH"
  if shutil.which(MDLINT_EXE) is None:
    pytest.fail(f"{MDLINT_EXE} is not installed or not on PATH - cannot run tests requiring it.\n" +
                "install with e.g. pip install pymarkdownlnt")

@pytest.fixture
def required_file(request):
  "ensure a specific file exists as a precondition to a test"

  file_path = request.param
  p = Path(file_path)
  if not p.is_file():
    pytest.fail(f"file {file_path} is required for test but does not exist")

  return file_path


####
# tests

def test_readme_exists():
  "README should exist"
  readme = Path("README.md")
  assert readme.is_file(), "Expected README.md to exist in the repository root."


@pytest.mark.parametrize(
  'required_file',
  ['README.md'],
  indirect=True,
)
def test_readme_starts_with_group_line(required_file):
  """
  First non-whitespace line of README should be '# Group N README',
  where N is a number from 0 to 99.
  """

  readme = Path(required_file)

  with readme.open("r", encoding="utf-8") as ifp:
    for line in ifp:
      stripped = line.strip()
      if stripped:
        assert re.fullmatch(r"# Group ([1-9][0-9]?) README", stripped), (
          f"First non-empty line of README.md should be of the form '# Group N README', "
          f"where N is a number from 1 to 99. Got: {stripped!r}"
        )
        break
    else:
      assert False, "README.md is empty or contains only whitespace."

def test_report_exists():
  report = Path(REPORT_FILENAME)
  assert report.is_file(), \
    "Expected file 'project-phase1-report.md' to exist in the repository root."


@pytest.mark.parametrize(
  "required_file",
  ["project-phase1-report.md"],
  indirect=True
)
def test_report_passes_linter(md_linter_available, required_file):
  """
  Lint the report with pymarkdownlnt.
  """

  _ = md_linter_available

  report_filename = required_file

  # Allow output from linter go to normal destinations (stdout/stderr)
  result = subprocess.run(
    ["pymarkdownlnt", "scan", report_filename],
    stdout=None,
    stderr=None,
    check=False,
  )
  assert result.returncode == 0, (
    f"'pymarkdownlnt scan {report_filename}' failed. See above for output."
  )


@pytest.mark.parametrize(
  "required_file",
  ["project-phase1-report.md"],
  indirect=True
)
def test_report_converts_to_pdf(pandoc_available, weasyprint_available, required_file):
  _ = pandoc_available
  _ = weasyprint_available
  report_filename = required_file
  output_pdf = REPORT_FILENAME.replace(".md", ".pdf")

  assert Path(report_filename).is_file(), f"{report_filename} must exist to convert to PDF."

  result = subprocess.run(
    [
      PANDOC_EXE,
      '--shift-heading-level-by=-1', # use first heading as title
      '--from=gfm', # use GitHub-flavoured markdown dialect
      "-o", str(output_pdf),
      "--pdf-engine=weasyprint", # convert using weasyprint
      report_filename,
    ],
    stdout=None,
    stderr=None,
    check=False,
  )
  assert result.returncode == 0, (
    f"Pandoc failed to convert {report_filename} to PDF using WeasyPrint. See above for output."
  )

