export function formatText(value: string) {
  const cleanValue = value.trim().toLowerCase();

  return cleanValue.charAt(0).toUpperCase() + cleanValue.slice(1);
}
